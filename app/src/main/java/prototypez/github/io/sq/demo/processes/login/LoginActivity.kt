package prototypez.github.io.sq.demo.processes.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.ObservableTransformer
import prototypez.github.io.sq.ActivityResult
import prototypez.github.io.sq.Sq
import prototypez.github.io.sq.activity.SqActivity
import prototypez.github.io.sq.demo.processes.login.steps.LoginByPwdFragment
import prototypez.github.io.sq.demo.processes.login.steps.LoginBySmsFragment
import prototypez.github.io.sq.demo.util.LoginInfo
import prototypez.github.io.sq.util.IntentBuilder

/**
 * 登陆流程
 */
class LoginActivity : SqActivity() {

    private lateinit var loginByPwdFragment: LoginByPwdFragment
    private lateinit var loginBySmsFragment: LoginBySmsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginByPwdFragment = findOrCreateFragment(LoginByPwdFragment::class.java)
        loginBySmsFragment = findOrCreateFragment(LoginBySmsFragment::class.java)

        if (savedInstanceState == null) {
            push(loginByPwdFragment)
        }

        // 流程控制逻辑

        // 用户名密码验证
        loginByPwdFragment.loginByPwdCallback = { needSmsVerify, user ->
            if (needSmsVerify) {
                // 进入短信验证流程
                loginBySmsFragment.setParams(user)
                push(loginBySmsFragment)
            } else {
                // 直接登录成功
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

        // 用户名密码验证 - 短信验证
        loginBySmsFragment.loginBySmsCallback =  { user ->
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



        // 用户名密码验证 - 子流程(直接注册成功)
        loginByPwdFragment.registerCallback = { user ->

            // 注册成功也视为登录成功的一种，需要通知流程发起者登录成功
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

    companion object {

        private const val REQUEST_LOGIN = 401

        // 默认情况，如果相同页面中只有一处操作需要确保登录可用
        fun ensureLoginWithContext(activity: AppCompatActivity): ObservableTransformer<Bundle, Bundle> {
            return ensureLoginWithContext(activity, REQUEST_LOGIN)
        }

        // 复杂情况，如果相同页面中有多处操作需要确保登录可用，那么需要通过 requestCode 区分请求由哪个操作发起
        fun ensureLoginWithContext(activity: AppCompatActivity, requestCode: Int): ObservableTransformer<Bundle, Bundle> {
            return ObservableTransformer { upstream ->
                upstream.subscribe { contextData ->
                    if (LoginInfo.isLogin) {
                        Sq.insertActivityResult(activity,
                                ActivityResult(
                                        requestCode,
                                        Activity.RESULT_OK,
                                        IntentBuilder.newInstance()
                                                .putExtra("user", LoginInfo.currentLoginUser)
                                                .build(), null,
                                        contextData
                                )
                        )
                    } else {
                        Sq.startActivityForResult(
                                activity,
                                makeIntentForLogin(activity),
                                requestCode,
                                contextData
                        )
                    }
                }

                Sq.obtainActivityResult(activity)
                        .filter { ar -> ar.requestCode == requestCode }
                        .filter { ar -> ar.resultCode == Activity.RESULT_OK }
                        .map { it.requestContextData }
            }
        }

        /**
         * 确保已登录，不需要保存流程触发点的上下文的简单情况
         */
        fun <T> ensureLogin(activity: AppCompatActivity): ObservableTransformer<T, ActivityResult> {
            return ObservableTransformer { upstream ->
                upstream.subscribe { _ ->
                    if (LoginInfo.isLogin) {
                        Sq.insertActivityResult(activity,
                                ActivityResult(
                                        REQUEST_LOGIN,
                                        Activity.RESULT_OK,
                                        IntentBuilder.newInstance()
                                                .putExtra("user", LoginInfo.currentLoginUser)
                                                .build()
                                )
                        )
                    } else {
                        Sq.startActivityForResult(
                                activity,
                                makeIntentForLogin(activity),
                                REQUEST_LOGIN
                        )
                    }
                }

                Sq.obtainActivityResult(activity)
                        .filter { ar -> ar.requestCode == REQUEST_LOGIN }
                        .filter { ar -> ar.resultCode == Activity.RESULT_OK }
            }
        }

        private fun makeIntentForLogin(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}


