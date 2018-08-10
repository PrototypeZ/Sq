package prototypez.github.io.sq.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.github.prototypez.savestate.core.annotation.AutoRestore
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import prototypez.github.io.sq.demo.entity.Item
import prototypez.github.io.sq.demo.processes.auth.AuthActivity
import prototypez.github.io.sq.demo.processes.comment.CommentActivity
import prototypez.github.io.sq.demo.processes.login.LoginActivity
import prototypez.github.io.sq.util.BundleBuilder

/**
 * 评论操作，绑定登录流程的 RequestCode
 */
private const val REQUEST_CODE_LOGIN_FOR_COMMENT = 1053

/**
 * 点赞操作，绑定登录流程的 RequestCode
 */
private const val REQUEST_CODE_LOGIN_FOR_LIKE = 1054


class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    internal var itemLikeClicks = PublishSubject.create<Int>()
    internal var itemCommentClicks = PublishSubject.create<Int>()

    @AutoRestore
    var items: Array<Item>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_list.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter()
        rv_list.adapter = adapter

        // 点赞 (演示登录流程中的流程嵌套，登录流程包含注册和找回密码两个子流程)
        itemLikeClicks
                .map { index -> BundleBuilder.newInstance().putInt("index", index).build() }
                .compose(LoginActivity.ensureLoginWithContext(this, REQUEST_CODE_LOGIN_FOR_LIKE))
                .map { bundle -> bundle.getInt("index") }
                .subscribe { index ->
                    items!![index].favCount += 1
                    adapter.notifyItemChanged(index)
                }


        // 评论 (演示流程组合，评论需要同时做完登录和实名认证两个流程)
        itemCommentClicks
                .map { index -> BundleBuilder.newInstance().putInt("index", index).build() }
                .compose(LoginActivity.ensureLoginWithContext(this, REQUEST_CODE_LOGIN_FOR_COMMENT))
                .compose(AuthActivity.ensureAuth(this))
                .compose(CommentActivity.startComment(this))
                .map { bundle -> bundle.getInt("index") }
                .subscribe { index ->
                    items!![index].commentCount += 1
                    adapter.notifyItemChanged(index)
                }
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.title)
        var image: ImageView = itemView.findViewById(R.id.image)
        var comment: TextView = itemView.findViewById(R.id.comment)
        var favIcon: ImageView = itemView.findViewById(R.id.fav_icon)
        var favNum: TextView = itemView.findViewById(R.id.favor)
    }

    internal inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        init {
            if (items == null) {
                items = arrayOf(Item("电视剧西游记挣了多少钱？",
                        R.drawable.t1, 10, 5, false), Item("史泰龙前妻宣布怀孕，与第5任老公生第5胎！网友：把史泰龙害好惨",
                        R.drawable.t2, 5, 10, false), Item("细数女星结婚,时的伴娘服，阿娇最良心，Angelababy的最差劲",
                        R.drawable.t3, 3, 35, false), Item("《放羊的星星》2018年启动重拍，主角人选大家还满意吗？",
                        R.drawable.t4, 123, 4, false), Item("参加湖南卫视的《变形计》的孩子们现在都怎么样了？",
                        R.drawable.t5, 0, 0, false), Item("易烊千玺高考前爆蓄须照，荷尔蒙爆表引迷妹尖叫：鼻血止不住了！",
                        R.drawable.t6, 3, 0, false), Item("李亚鹏谈与王菲离婚眼含泪水，大半年才释然，但仍有最愧疚的人",
                        R.drawable.t7, 19, 0, false))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.layout_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items!![position]
            holder.image.setImageResource(item.imageRes)
            holder.title.text = item.title
            holder.comment.text = String.format("评论 %d", item.commentCount)
            holder.favNum.text = item.favCount.toString()
            holder.favIcon.setImageResource(if (item.fav) R.drawable.favorite else R.drawable.favorite_border)

            holder.favIcon.setOnClickListener { _ -> itemLikeClicks.onNext(position) }
            holder.comment.setOnClickListener { _ -> itemCommentClicks.onNext(position) }
        }

        override fun getItemCount(): Int {
            return items!!.size
        }
    }
}
