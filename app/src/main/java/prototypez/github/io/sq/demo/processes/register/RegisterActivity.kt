package prototypez.github.io.sq.demo.processes.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

import io.github.prototypez.savestate.core.annotation.AutoRestore
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import prototypez.github.io.sq.Sq
import prototypez.github.io.sq.activity.SqActivity
import prototypez.github.io.sq.demo.entity.User
import prototypez.github.io.sq.demo.processes.register.steps.PhoneRegisterFragment
import prototypez.github.io.sq.demo.processes.register.steps.PwdSetFragment
import prototypez.github.io.sq.demo.processes.register.steps.SmsCheckForRegisterFragment
import prototypez.github.io.sq.util.IntentBuilder

class RegisterActivity : SqActivity() {

    private lateinit var phoneRegisterFragment: PhoneRegisterFragment
    private lateinit var pwdSetFragment: PwdSetFragment
    private lateinit var smsCheckForRegisterFragment: SmsCheckForRegisterFragment

    @AutoRestore
    lateinit var phone: String

    @AutoRestore
    lateinit var session: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        phoneRegisterFragment = findOrCreateFragment(PhoneRegisterFragment::class.java)
        smsCheckForRegisterFragment = findOrCreateFragment(SmsCheckForRegisterFragment::class.java)
        pwdSetFragment = findOrCreateFragment(PwdSetFragment::class.java)

        // 第一步，输入手机号
        if (savedInstanceState == null) {
            push(phoneRegisterFragment)
        }

        phoneRegisterFragment.phoneInputCallback = { phone, session ->
            this.phone = phone
            this.session = session

            // 第二部，验证短信验证码
            smsCheckForRegisterFragment.setParams(phone, session)
            push(smsCheckForRegisterFragment)
        }

        smsCheckForRegisterFragment.smsCheckForRegister = {
            // 第三步，设置密码
            pwdSetFragment.setParams(phone, session)
            push(pwdSetFragment)
        }


        pwdSetFragment.pwdSetCallback = { user ->
            // 流程完成，返回注册用户信息
            Sq.setResult(this, Activity.RESULT_OK,
                    IntentBuilder.newInstance()
                            .putExtra("user", user)
                            .build()
            )
            finish()
        }
    }

    companion object {

        private const val REQUEST_CODE_LOGIN = 1001

        // 注册流程
        fun <T> register(fragment: Fragment): ObservableTransformer<T, User> {
            return ObservableTransformer { upstream ->
                upstream.subscribe { _ ->
                    Sq.startActivityForResult(
                            fragment,
                            IntentBuilder.newInstance(fragment.context, RegisterActivity::class.java).build(),
                            REQUEST_CODE_LOGIN
                    )
                }

                Sq.obtainActivityResult(fragment)
                        .filter { ar -> ar.requestCode == REQUEST_CODE_LOGIN }
                        .filter { ar -> ar.resultCode == Activity.RESULT_OK }
                        .map { ar -> ar.data.getSerializableExtra("user") as User }
            }
        }
    }

}
