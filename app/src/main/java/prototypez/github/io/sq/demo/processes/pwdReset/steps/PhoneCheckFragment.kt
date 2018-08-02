package prototypez.github.io.sq.demo.processes.pwdReset.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_phone_input.*
import prototypez.github.io.sq.demo.R
import java.util.*

class PhoneCheckFragment : Fragment() {

    lateinit var phoneCheckCallback: (phone: String, session: String) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_ok.setOnClickListener{ _ ->
            // 发起请求，这里直接成功
            val session = UUID.randomUUID().toString()
            phoneCheckCallback(et_phone.text.toString(), session)
        }
    }


}
