package com.yywzs.cop_turchat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yywzs.cop_turchat.MainActivity;
import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.bean.Post;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class Receive extends AppCompatActivity {
    private TextView nickname,title,content,time;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        initView();
        initData();
    }

    private void initData() {
        Intent in = getIntent();
        String nickname_text = in.getStringExtra("nickname");
        String title_text = in.getStringExtra("title");
        String content_text = in.getStringExtra("content");
        String time_text = in.getStringExtra("time");
        nickname.setText(nickname_text);
        title.setText(title_text);
        content.setText(content_text);
        time.setText(time_text);

        /*String id = in.getStringExtra("id");
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (e == null){
                    nickname.setText(post.getNickname());
                    title.setText(post.getTitle());
                    content.setText(post.getContent());
                    time.setText(post.getCreatedAt());
                }else {
                    Toast.makeText(Receive.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void initView() {
        nickname = findViewById(R.id.nickname);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        time = findViewById(R.id.time);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Receive.this, MainActivity.class));
            }
        });
    }
}
