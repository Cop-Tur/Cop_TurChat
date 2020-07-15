package com.yywzs.cop_turchat.fragment;


import android.content.Intent;
import android.os.Bundle;
import com.yywzs.cop_turchat.bean.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.adapter.MessageAdapter;
import com.yywzs.cop_turchat.bean.User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentMessage extends androidx.fragment.app.Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    List<Message> data;
    MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmessage,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        Bmob.initialize(getActivity(),"57b3ab3017df6c3be31da6ca56d951bc");

        Refresh();
        //srlayout.setColorSchemeResources("#E7E5E5","#E7E5E5","#E7E5E5");
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {
        User current = BmobUser.getCurrentUser(User.class);
        BmobQuery<Message> messageBmobQuery = new BmobQuery<>();
        messageBmobQuery.order("createAt");
        messageBmobQuery.setLimit(1000);
        messageBmobQuery.addWhereEqualTo("receiver",current);
        messageBmobQuery.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                srlayout.setRefreshing(false);
                if (e == null){
                    data = list;
                    messageAdapter = new MessageAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(messageAdapter);
                }else {
                    System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+e);
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        rv = getActivity().findViewById(R.id.recyclerviewmessage);
        srlayout = getActivity().findViewById(R.id.swipmessage);

        /*Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.backg));*/


        /*String htmlFor02 = "<img src='" + R.drawable.ic_search_black_24dp + "'>"+"有哪些好玩的微信小游戏";
        search_text.setText(Html.fromHtml(htmlFor02, (Html.ImageGetter) source -> {
            int id = Integer.parseInt(source);
            Drawable drawable = getResources().getDrawable(id, null);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth() ,
                    drawable.getIntrinsicHeight());
            return drawable;
        }));*/

    }

}
