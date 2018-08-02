package prototypez.github.io.sq.demo.processes.register.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import prototypez.github.io.sq.demo.R
import prototypez.github.io.sq.demo.entity.User
import prototypez.github.io.sq.util.BundleBuilder
import kotlinx.android.synthetic.main.fragment_set_pwd.*
import java.util.*

class PwdSetFragment : Fragment() {

    lateinit var phone: String
    lateinit var session: String

    lateinit var pwdSetCallback: (User) -> Unit

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
        return inflater.inflate(R.layout.fragment_set_pwd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_confirm.setOnClickListener { _ ->

            if (!TextUtils.equals(et_password.text.toString(), et_password_confirm.text.toString())) {
                Toast.makeText(context, "密码不一致", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            // 发起请求，注册用户，这里直接 Mock 一个注册成功

            pwdSetCallback(User(
                    UUID.randomUUID().toString(),
                    phone,
                    phone,
                    "",
                    15,
                    false
            ))
        }
    }
}
