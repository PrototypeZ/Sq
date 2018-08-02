package prototypez.github.io.sq.demo.processes.comment

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.ObservableTransformer
import kotlinx.android.synthetic.main.activity_comment.*
import prototypez.github.io.sq.Sq
import prototypez.github.io.sq.demo.R
import prototypez.github.io.sq.util.IntentBuilder

class CommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        btn_confirm.setOnClickListener { _ ->
            val commentContent = et_content.text.toString()
            // 发起请求，评论，这里直接评论成功

            Sq.setResult(this, Activity.RESULT_OK, null)
            finish()
        }
    }

    companion object {
        private const val REQUEST_CODE_COMMENT = 1000

        fun startComment(activity: AppCompatActivity): ObservableTransformer<Bundle, Bundle> {
            return ObservableTransformer { upstream ->
                upstream.subscribe { contextData ->
                    Sq.startActivityForResult(
                            activity,
                            IntentBuilder.newInstance(activity, CommentActivity::class.java).build(),
                            REQUEST_CODE_COMMENT,
                            contextData
                    )
                }

                Sq.obtainActivityResult(activity)
                        .filter { ar -> ar.requestCode == REQUEST_CODE_COMMENT }
                        .filter { ar -> ar.resultCode == Activity.RESULT_OK }
                        .map { ar -> ar.requestContextData }
            }
        }
    }

}