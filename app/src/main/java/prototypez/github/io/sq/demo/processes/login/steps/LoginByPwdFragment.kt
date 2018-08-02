package prototypez.github.io.sq.demo.processes.login.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_login_by_pwd.*
import prototypez.github.io.sq.demo.R
import prototypez.github.io.sq.demo.entity.User
import prototypez.github.io.sq.demo.processes.pwdReset.PwdResetActivity
import prototypez.github.io.sq.demo.processes.register.RegisterActivity
import java.util.*

class LoginByPwdFragment : Fragment() {

    lateinit var loginByPwdCallback: (needSmsVerify: Boolean, user: User) -> Unit
    lateinit var registerCallback: (User) -> Unit

    private val random = Random()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_by_pwd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in.setOnClickListener {
            val phone = et_phone.text.toString()
            val pwd = et_password.text.toString()

            if (random.nextBoolean()) {
                // 50% chance to enter sms verify
                loginByPwdCallback(true, User(
                        UUID.randomUUID().toString(),
                        "Jack",
                        et_phone.text.toString()
                ))
            } else {
                // 50% chance login ok immediately
                loginByPwdCallback(false, User(
                        UUID.randomUUID().toString(),
                        "Jack",
                        et_phone.text.toString(),
                        "Shanghai",
                        29,
                        false
                ))
            }
        }

        // 嵌套流程：注册
        RxView.clicks(tv_register)
                .compose(RegisterActivity.register(this))
                .subscribe { user ->
                    registerCallback(user)
                }


        // 嵌套流程：忘记密码
        RxView.clicks(tv_reset_pwd)
                .compose(PwdResetActivity.pwdReset(this))
                .subscribe()
    }
}
