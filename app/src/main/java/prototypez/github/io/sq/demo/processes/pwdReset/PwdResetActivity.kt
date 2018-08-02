package prototypez.github.io.sq.demo.processes.pwdReset

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import io.github.prototypez.savestate.core.annotation.AutoRestore
import io.reactivex.ObservableTransformer
import prototypez.github.io.sq.ActivityResult
import prototypez.github.io.sq.Sq
import prototypez.github.io.sq.activity.SqActivity
import prototypez.github.io.sq.demo.processes.pwdReset.steps.PhoneCheckFragment
import prototypez.github.io.sq.demo.processes.pwdReset.steps.PwdResetFragment
import prototypez.github.io.sq.demo.processes.pwdReset.steps.SmsCheckForPwdResetFragment
import prototypez.github.io.sq.util.IntentBuilder

class PwdResetActivity : SqActivity() {

    lateinit var phoneCheckFragment: PhoneCheckFragment
    lateinit var smsCheckForPwdResetFragment: SmsCheckForPwdResetFragment
    lateinit var pwdResetFragment: PwdResetFragment

    @AutoRestore
    lateinit var phone: String

    @AutoRestore
    lateinit var session: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        phoneCheckFragment = findOrCreateFragment(PhoneCheckFragment::class.java)
        smsCheckForPwdResetFragment = findOrCreateFragment(SmsCheckForPwdResetFragment::class.java)
        pwdResetFragment = findOrCreateFragment(PwdResetFragment::class.java)

        // 第一步： 输入手机号
        if (savedInstanceState == null) {
            push(phoneCheckFragment)
        }

        phoneCheckFragment.phoneCheckCallback = { phone, session ->
            this.phone = phone
            this.session = session

            // 第二步：验证短信验证码
            smsCheckForPwdResetFragment.setParams(phone, session)
            push(smsCheckForPwdResetFragment)
        }

        smsCheckForPwdResetFragment.smsCheckCallback = {
            // 第三步：重置密码
            pwdResetFragment.setParams(phone, session)
            push(pwdResetFragment)
        }

        pwdResetFragment.pwdResetCallback = {
            // 重置密码完成
            Sq.setResult(this, Activity.RESULT_OK)
            finish()
        }

    }


    companion object {

        private const val REQUEST_CODE_PWD_RESET = 1014

        fun <T> pwdReset(fragment: Fragment): ObservableTransformer<T, ActivityResult> {
            return ObservableTransformer { upstream ->

                upstream.subscribe { _ ->
                    Sq.startActivityForResult(
                            fragment,
                            IntentBuilder.newInstance(fragment.context, PwdResetActivity::class.java).build(),
                            REQUEST_CODE_PWD_RESET
                    )
                }

                Sq.obtainActivityResult(fragment)
                        .filter { ar -> ar.requestCode == REQUEST_CODE_PWD_RESET }
                        .filter { ar -> ar.resultCode == Activity.RESULT_OK }
            }
        }
    }
}
