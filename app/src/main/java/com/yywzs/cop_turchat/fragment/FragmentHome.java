package com.yywzs.cop_turchat.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.activity.Search;
import com.yywzs.cop_turchat.adapter.PostAdapter;
import com.yywzs.cop_turchat.bean.Post;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentHome extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    List<Post> data;
    PostAdapter postAdapter;
    private TextView search_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthome,container,false);
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
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.order("-createAt");
        postBmobQuery.setLimit(1000);
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                srlayout.setRefreshing(false);
                if (e == null){
                    data = list;
                    postAdapter = new PostAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(postAdapter);
                }else {
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        rv = getActivity().findViewById(R.id.recyclerview);
        srlayout = getActivity().findViewById(R.id.swip);
        search_text = getView().findViewById(R.id.search_text);

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

        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), Search.class));
            }
        });
    }
}
