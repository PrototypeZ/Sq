# Sq
[ ![Download](https://api.bintray.com/packages/prototypez/maven/sq/images/download.svg) ](https://bintray.com/prototypez/maven/sq/_latestVersion)

![](https://raw.githubusercontent.com/PrototypeZ/Sq/master/sq-logo.png)

更流畅的方式使用 `startActivityForResult` 和 `onActivityResult`, 以及封装你的业务流程。

同时具有更好的健壮性，在 Activity / 应用进程 销毁重建的场景下依然可以正常工作。

## 从 Activity 中得到一个结果

以登录流程为例，App 中会有很多操作会触发登录流程, 例如点赞操作：

```kotlin

override fun onCreate(savedInstanceState: Bundle?) {
    likeBtn.setOnClickListener { _ ->
        if (LoginInfo.isLogin()) {
            doLike()
        } else {
            startActivityForResult(
                Intent(this, LoginActivity::class.java)
                REQUEST_CODE_LOGIN
            )
        }
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        REQUEST_CODE_LOGIN -> {
            if (resultCode == Activity.RESULT_OK) {
                doLike()
            }
        }
    }
}

```

上面的写法，虽然可以正常工作，但是显得过于散乱，我们使用 **Sq** 来优化代码。

## 使用 Sq 调用登录流程

使用 `RxJava` 中的 `compose` 操作符，在点击事件与点赞操作之间插入登录检测操作：

```kotlin
RxView.clicks(likeBtn)
    .compose(LoginActivity.ensureLogin(this))
    .subscribe {_ -> doLike()}
```

登录流程不再变得难以复用，从此可以在任何操作前插入前置登录检测流程。`ensureLogin` 方法的实现：

```kotlin
fun <T> ensureLogin(activity: AppCompatActivity): ObservableTransformer<T, ActivityResult> {
    return ObservableTransformer { upstream ->
        upstream.subscribe { _ ->
            if (LoginInfo.isLogin()) {
                Sq.insertActivityResult(activity, ActivityResult(REQUEST_CODE_LOGIN, Activity.RESULT_OK, null))
            } else {
                Sq.startActivityForResult(activity, Intent(activity, LoginActivity::class.java), REQUEST_CODE_LOGIN)
            }
        }
        Sq.obtainActivityResult(activity)
                .filter { ar -> ar.requestCode == REQUEST_CODE_LOGIN }
                .filter { ar -> ar.resultCode == Activity.RESULT_OK }
    }
}
```

## 集成

```groovy
dependencies {
    // other dependencies
    // ...
    implementation 'io.github.prototypez:sq:${latest_version}'
}

```

当前最新版本：[ ![Download](https://api.bintray.com/packages/prototypez/maven/sq/images/download.svg) ](https://bintray.com/prototypez/maven/sq/_latestVersion)

## 高级用法

### 流程组合

场景: 用户点击评论，需要依次确保已登录以及已实名认证，如果未登录则带到登录界面，如果未实名认证则带到实名认证界面，完成流程后自动进入后续步骤。

```kotlin
btnComment
    .compose(LoginActivity.ensureLogin(this))
    .compose(AuthActivity.ensureAuth(this))
    .subscribe {_ -> startCommentActivity(this)}
```

### 流程发起点上下文保存

场景：ListView / RecyclerView 中的点击事件发起流程，需要保存点击事件发起时的上下文（哪个 Item 发起的流程），这样流程完成时才能正常更新发起流程的那个 Item。

```kotlin
itemLikeClicks
        .map { index -> BundleBuilder.newInstance().putInt("index", index).build() }
        .compose(LoginActivity.ensureLoginWithContext(this))
        .map { bundle -> bundle.getInt("index") }
        .subscribe { index ->
            items[index].favCount += 1
            adapter.notifyItemChanged(index)
        }
```

对应 `ensureLoginWithContext` 的实现：

```kotlin
fun ensureLoginWithContext(activity: AppCompatActivity): ObservableTransformer<Bundle, Bundle> {
    return ObservableTransformer { upstream ->
        upstream.subscribe { contextData ->
            if (LoginInfo.isLogin()) {
                Sq.insertActivityResult(activity, ActivityResult(REQUEST_CODE_LOGIN, Activity.RESULT_OK, null, contextData))
            } else {
                Sq.startActivityForResult(activity, Intent(activity, LoginActivity::class.java), REQUEST_CODE_LOGIN, contextData)
            }
        }
        Sq.obtainActivityResult(activity)
                .filter { ar -> ar.requestCode == REQUEST_CODE_LOGIN }
                .filter { ar -> ar.resultCode == Activity.RESULT_OK }
                .map { it.requestContextData }
    }
}
```

与此同时，需要把原来 `LoginActivity` 中的 `Activity.setResult` 方法替换为 `Sq.setResult` 方法。

### 区分同一页面中不同事件发起的相同流程

场景：某个页面，用户可以进行点赞和评论两个操作，这两个操作都需要确保登录态，直接使用前面的 `ensureLogin` 方法的话会导致登录流程完成以后无法区分具体是由哪个操作出发的登录流程，导致两种事件的回调都会被触发，我们需要重载 `ensureLogin` 方法:

```kotlin
fun <T> ensureLogin(activity: AppCompatActivity, requestCode: Int): ObservableTransformer<T, ActivityResult> {
    return ObservableTransformer { upstream ->
        upstream.subscribe { _ ->
            if (LoginInfo.isLogin()) {
                Sq.insertActivityResult(activity, ActivityResult(requestCode, Activity.RESULT_OK, null))
            } else {
                Sq.startActivityForResult(activity, Intent(activity, LoginActivity::class.java), requestCode)
            }
        }
        Sq.obtainActivityResult(activity)
                .filter { ar -> ar.requestCode == requestCode }
                .filter { ar -> ar.resultCode == Activity.RESULT_OK }
    }
}
```

这样，我们便可以在两种不同事件上区分触发相同流程的回调了：

```kotlin
btnComment
    .compose(LoginActivity.ensureLogin(this, REQUEST_CODE_LOGIN_FOR_COMMENT))
    .subscribe {_ -> startCommentActivity(this)}

btnLike
    .compose(LoginActivity.ensureLogin(this, REQUEST_CODE_LOGIN_FOR_LIKE))
    .subscribe {_ -> doLike()}
```

## 流程内部步骤封装

首先约定:
1. 一个业务流程对应一个 `Activity` , 这个流程内部的所有步骤都由 `Fragment` 来实现;
2. `Fragment` 负责完成本步骤应该完成的任务，并把本步骤的结果通知 `Activity`;
3. `Activity` 负责整个流程对外接口，同时负责收集各步骤的结果，进行步骤之间的流转调度以及结束流程;

`Fragment` 可以由你自己实现， **Sq** 提供了 `SqActivity` 类，帮助你实现上面约定中的 `Activity`，你可以从 `SqActivity` 继承下来， 实现你自己的业务流程的 `Activity` 。下面以一个带短信验证码的两步验证的登录流程为例：

```kotlin
class LoginActivity : SqActivity() {

    private lateinit var loginByPwdFragment: LoginByPwdFragment
    private lateinit var loginBySmsFragment: LoginBySmsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginByPwdFragment = findOrCreateFragment(LoginByPwdFragment::class.java)
        loginBySmsFragment = findOrCreateFragment(LoginBySmsFragment::class.java)

        // 第一步，进入用户名密码验证界面
        if (savedInstanceState == null) {
            push(loginByPwdFragment)
        }

        // 第一步用户名密码验证的结果回调
        loginByPwdFragment.loginByPwdCallback = { needSmsVerify, user ->
            if (needSmsVerify) {
                // 进入第二步，短信验证步骤
                loginBySmsFragment.setParams(user)
                push(loginBySmsFragment)
            } else {
                // 直接登录成功，流程结束
                LoginInfo.currentLoginUser = user
                Sq.setResult(
                        this@LoginActivity,
                        Activity.RESULT_OK,
                        IntentBuilder.newInstance()
                                .putExtra("user", user)
                                .build()
                )
                finish()
            }
        }

        // 第二步短信验证的结果回调
        loginBySmsFragment.loginBySmsCallback =  { user ->
            // 验证成功，登录成功，流程结束
            LoginInfo.currentLoginUser = user
            Sq.setResult(
                    this@LoginActivity,
                    Activity.RESULT_OK,
                    IntentBuilder.newInstance()
                            .putExtra("user", user)
                            .build()
            )
            finish()
        }
    }
}
```

如果你不希望从 `SqActivity` 继承，你也可以从自己的 `Activity` 继承下来，`SqActivity` 只是提供了 `push` 和 `findOrCreateFragment` 这两个方法以供调用， 这两个方法同样存在于 `Sq` 这个类里，你也可以手动调用。

如果你并不想遵守上面的约定来封装业务流程，你可以仅仅只把 **Sq** 当成一个 `startActivityForResult` 的工具类。

## 参考资料

[如何优雅地构建易维护、可复用的 Android 业务流程](http://prototypez.github.io/2018/04/30/best-practices-of-android-process-management/)

[如何优雅地构建易维护、可复用的 Android 业务流程(二)](http://prototypez.github.io/2018/04/30/best-practices-of-android-process-management-2/)

## LICENSE

    Copyright (c) 2016-present, Sq Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.