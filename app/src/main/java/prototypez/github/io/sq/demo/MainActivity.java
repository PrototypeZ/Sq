package prototypez.github.io.sq.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import prototypez.github.io.sq.demo.entity.Item;


public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new Adapter());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView comment;
        ImageView favIcon;
        TextView favNum;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            comment = itemView.findViewById(R.id.comment);
            favIcon = itemView.findViewById(R.id.fav_icon);
            favNum = itemView.findViewById(R.id.favor);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        Item[] items = new Item[]{
                new Item("电视剧西游记挣了多少钱？",
                        R.drawable.t1, 10, 5, false),
                new Item("史泰龙前妻宣布怀孕，与第5任老公生第5胎！网友：把史泰龙害好惨",
                        R.drawable.t2, 5, 10, false),
                new Item("细数女星结婚,时的伴娘服，阿娇最良心，Angelababy的最差劲",
                        R.drawable.t3, 3, 35, false),
                new Item("《放羊的星星》2018年启动重拍，主角人选大家还满意吗？",
                        R.drawable.t4, 123, 4, false),
                new Item("参加湖南卫视的《变形计》的孩子们现在都怎么样了？",
                        R.drawable.t5, 0, 0, false),
                new Item("易烊千玺高考前爆蓄须照，荷尔蒙爆表引迷妹尖叫：鼻血止不住了！",
                        R.drawable.t6, 3, 0, false),
                new Item("李亚鹏谈与王菲离婚眼含泪水，大半年才释然，但仍有最愧疚的人",
                        R.drawable.t7, 19, 0, false)
        };

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.layout_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item item = items[position];
            holder.image.setImageResource(item.imageRes);
            holder.title.setText(item.title);
            holder.comment.setText(String.format("评论 %d", item.commentCount));
            holder.favNum.setText(String.valueOf(item.favCount));
            holder.favIcon.setImageResource(item.fav ? R.drawable.favorite : R.drawable.favorite_border);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }
    }
}
