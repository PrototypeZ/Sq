package prototypez.github.io.sq.demo.processes.register.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import prototypez.github.io.sq.demo.R
import prototypez.github.io.sq.util.BundleBuilder
import kotlinx.android.synthetic.main.fragment_sms_verify.*

class SmsCheckForRegisterFragment : Fragment() {

    lateinit var smsCheckForRegister: () -> Unit

    private var phone: String? = null
    private var session: String? = null

    fun setParams(phone: String, session: String) {
        arguments = BundleBuilder.newInstance()
                .putString("phone", phone)
                .putString("session", session)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            phone = arguments!!.getString("phone")
            session = arguments!!.getString("session")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sms_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_hint.text = String.format("验证码已发送至: %s", phone)
        vc_code.setOnCodeFinishListener { content ->
            // 根据 phone 和 session 发起请求，验证是否正确，这里直接 Mock 请求成功
            smsCheckForRegister()
        }
    }
}
