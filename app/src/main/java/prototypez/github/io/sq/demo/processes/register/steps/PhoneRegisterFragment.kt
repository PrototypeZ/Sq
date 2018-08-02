package prototypez.github.io.sq.demo.processes.register.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import prototypez.github.io.sq.demo.R
import kotlinx.android.synthetic.main.fragment_phone_input.*
import java.util.*

class PhoneRegisterFragment : Fragment() {

    lateinit var phoneInputCallback: (String, String) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_ok.setOnClickListener{
            // Mock 请求成功，直接进入下一步
            phoneInputCallback(et_phone.text.toString(), UUID.randomUUID().toString())
        }
    }
}
