package prototypez.github.io.sq.demo.processes.auth

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.ObservableTransformer

import prototypez.github.io.sq.demo.R
import kotlinx.android.synthetic.main.activity_auth.*
import prototypez.github.io.sq.ActivityResult
import prototypez.github.io.sq.Sq
import prototypez.github.io.sq.demo.util.LoginInfo
import prototypez.github.io.sq.util.IntentBuilder

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        btn_confirm.setOnClickListener { _ ->
            val idNumber = et_id_number.text.toString()
            val realName = et_real_name.text.toString()

            // 发起实名认证请求，这里直接 Mock 成功
            LoginInfo.currentLoginUser!!.hasAuth = true
            Sq.setResult(
                    this,
                    Activity.RESULT_OK,
                    null
            )
            finish()
        }
    }


    companion object {

        private const val REQUEST_CODE_AUTH = 834

        fun ensureAuth(activity: AppCompatActivity): ObservableTransformer<Bundle, Bundle> {
            return ObservableTransformer { upstream ->

                upstream.subscribe { contextData ->
                    if (LoginInfo.currentLoginUser != null
                            && LoginInfo.currentLoginUser?.hasAuth != null) {
                        if (!LoginInfo.currentLoginUser?.hasAuth!!) {
                            Sq.startActivityForResult(
                                    activity,
                                    IntentBuilder.newInstance(activity, AuthActivity::class.java)
                                            .build(),
                                    REQUEST_CODE_AUTH,
                                    contextData
                            )
                        } else {
                            Sq.insertActivityResult(
                                    activity,
                                    ActivityResult(
                                            REQUEST_CODE_AUTH,
                                            Activity.RESULT_OK,
                                            null,
                                            null,
                                            contextData
                                    )
                            )
                        }
                    }
                }
                Sq.obtainActivityResult(activity)
                        .filter { ar -> ar.requestCode == REQUEST_CODE_AUTH }
                        .filter { ar -> ar.resultCode == Activity.RESULT_OK }
                        .map { ar -> ar.requestContextData }
            }
        }
    }
}
