package com.yywzs.cop_turchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.activity.Login;
import com.yywzs.cop_turchat.activity.Receive;
import com.yywzs.cop_turchat.bean.Message;
import com.yywzs.cop_turchat.bean.Post;
import com.yywzs.cop_turchat.bean.User;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Message> data;
    private final int N_TYPE = 0;
    private final int F_TYPE = 1;
    private int Max_num = 8;
    private Boolean isfoot = new Boolean(true);

    public MessageAdapter(Context context, List<Message> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageitem,parent,false);
        View footview = LayoutInflater.from(parent.getContext()).inflate(R.layout.footitem,parent,false);
        if (viewType == F_TYPE){
            return new RecyclerViewHolder(footview,F_TYPE);
        }else {
            return new RecyclerViewHolder(view,N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isfoot && getItemViewType(position) == F_TYPE){
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            recyclerViewHolder.loading.setText("加载中...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_num += 8;
                    notifyDataSetChanged();
                }
            },2000);
        }else {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            Message message = data.get(position);
            recyclerViewHolder.nickname.setText(message.getSendernickname());
            recyclerViewHolder.content.setText(message.getContent());
            recyclerViewHolder.time.setText(message.getCreatedAt());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(context).builder()
                            .setTitle("发送私信")
                            .setEditText("请输入内容");
                    myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User sender = BmobUser.getCurrentUser(User.class);
                            User receiver = message.getSender();
                            Message message = new Message();
                            message.setContent(myAlertInputDialog.getResult());
                            message.setSender(sender);
                            message.setReceiver(receiver);
                            message.setSendernickname(sender.getNickname());
                            message.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null){
                                        Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                                        myAlertInputDialog.dismiss();
                                    }else{
                                        Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            //Toast.makeText(getContext(), myAlertInputDialog.getResult(), Toast.LENGTH_SHORT).show();
                            //showMsg(myAlertInputDialog.getResult());
                            //myAlertInputDialog.dismiss();
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                            //showMsg("取消");
                            myAlertInputDialog.dismiss();
                        }
                    });
                    myAlertInputDialog.show();

//                    int position = recyclerViewHolder.getAdapterPosition();
//                    if (BmobUser.getCurrentUser(BmobUser.class) != null){
//                        Intent in = new Intent(context, Receive.class);
//                        in.putExtra("nickname",post.getNickname());
//                        in.putExtra("title",post.getTitle());
//                        in.putExtra("content",post.getContent());
//                        in.putExtra("time",post.getCreatedAt());
//                        //in.putExtra("id",data.get(position).getObjectId());
//                        context.startActivity(in);
//                    }else {
//                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, Login.class));
//
//                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == Max_num-1)
            return F_TYPE;
        else
            return N_TYPE;
    }

    @Override
    public int getItemCount() {
        if (data.size() < Max_num)
            return data.size();
        return Max_num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname,content,time,loading;

        public RecyclerViewHolder(View item, int f_type) {
            super(item);
            if (f_type == N_TYPE){
                nickname = item.findViewById(R.id.nickname);
                content = item.findViewById(R.id.content);
                time = item.findViewById(R.id.time);
            }else if (f_type == F_TYPE){
                loading = item.findViewById(R.id.foottext);
            }
        }
    }
}

