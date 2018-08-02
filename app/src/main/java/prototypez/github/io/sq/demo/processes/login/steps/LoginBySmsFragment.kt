package prototypez.github.io.sq.demo.processes.login.steps
import kotlinx.android.synthetic.main.fragment_sms_verify.*

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jyn.vcview.VerificationCodeView

import prototypez.github.io.sq.demo.R
import prototypez.github.io.sq.demo.entity.User
import prototypez.github.io.sq.util.BundleBuilder

class LoginBySmsFragment : Fragment() {

    lateinit var loginBySmsCallback: (User) -> Unit

    private lateinit var mUser: User

    fun setParams(user: User) {
        arguments = BundleBuilder.newInstance().putSerializable("user", user).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUser = arguments!!.getSerializable("user") as User
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sms_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_hint.text = String.format("验证码已发送至: %s", mUser.phone)

        vc_code.onCodeFinishListener = VerificationCodeView.OnCodeFinishListener {
            // mock success
            loginBySmsCallback(User(
                    mUser.id,
                    mUser.nickName,
                    mUser.phone,
                    "Shanghai",
                    29,
                    false
            ))
        }
    }
}
