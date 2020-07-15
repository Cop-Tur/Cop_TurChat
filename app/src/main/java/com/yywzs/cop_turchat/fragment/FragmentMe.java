package com.yywzs.cop_turchat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.activity.Login;
import com.yywzs.cop_turchat.activity.Setting;
import com.yywzs.cop_turchat.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class FragmentMe extends androidx.fragment.app.Fragment {

    private TextView telephone,nickname,area;
    private Button loginout;

    private LinearLayout mypush;
    private LinearLayout mycollect;
    private LinearLayout setting;

    private ImageView mine_gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmine,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        //加载我的信息
        getMyinfo();


/*        mypush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MyPush.class));
            }
        });*/

/*        mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MyCollect.class));
            }
        });*/

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Setting.class));
            }
        });

        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), Login.class));
                //
                getActivity().finish();
            }
        });

    }


    private void getMyinfo() {
        //加载个人信息
        BmobUser bu = BmobUser.getCurrentUser(BmobUser.class);
        String Id = bu.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    nickname.setText(user.getNickname());
                    telephone.setText(user.getMobilePhoneNumber()==null ?"未设置":user.getMobilePhoneNumber().trim());
                    area.setText(user.getArea()==null?"未设置":user.getArea().trim());
                    if (user.getGender()!=null){
                        if (user.getGender().equals("男")){
                            mine_gender.setImageResource(R.mipmap.man);
                        }else {
                            mine_gender.setImageResource(R.mipmap.girl);
                        }
                    }else {
                        mine_gender.setImageResource(R.mipmap.girl);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        telephone = getActivity().findViewById(R.id.telephone);
        nickname = getActivity().findViewById(R.id.nickname);
        area = getActivity().findViewById(R.id.area);
        mypush = getActivity().findViewById(R.id.mypush);
        mycollect = getActivity().findViewById(R.id.mycollect);
        mine_gender = getActivity().findViewById(R.id.mine_gender);
        setting = getActivity().findViewById(R.id.setting);
        loginout = getActivity().findViewById(R.id.loginout);
    }


    //再次来到这个界面
    @Override
    public void onResume() {
        super.onResume();
    }
}
