package prototypez.github.io.sq.demo.processes.pwdReset.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sms_verify.*
import prototypez.github.io.sq.demo.R
import prototypez.github.io.sq.util.BundleBuilder

class SmsCheckForPwdResetFragment : Fragment() {

    lateinit var smsCheckCallback: () -> Unit
    lateinit var phone: String
    lateinit var session: String

    fun setParams(phone: String, session: String) {
        arguments = BundleBuilder.newInstance()
                .putString("phone", phone)
                .putString("session", session)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        phone = arguments!!.getString("phone")
        session = arguments!!.getString("session")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sms_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_hint.text = String.format("验证码已发送至: %s", phone)
        vc_code.setOnCodeFinishListener {
            // 发起请求验证验证码，这里直接成功
            smsCheckCallback()
        }
    }
}
