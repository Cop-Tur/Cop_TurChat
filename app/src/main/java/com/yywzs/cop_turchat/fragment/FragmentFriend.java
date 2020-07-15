package com.yywzs.cop_turchat.fragment;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.adapter.MyAdapter;
import com.yywzs.cop_turchat.bean.ContentInfo;
import com.yywzs.cop_turchat.bean.Message;
import com.yywzs.cop_turchat.bean.TitleInfo;
import com.yywzs.cop_turchat.bean.User;
import com.yywzs.cop_turchat.utils.EListViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class FragmentFriend extends Fragment implements MyAdapter.OnGroupExpanded {

    private ExpandableListView mElistview;
    TitleInfo titleInfo1 = new TitleInfo();
    TitleInfo titleInfo2 = new TitleInfo();
    TitleInfo titleInfo3 = new TitleInfo();
    TitleInfo titleInfo4 = new TitleInfo();
    public String[] titleStrings = {"特别关心", "我的家人", "我的好友", "陌生人"};


    public List<User> first = new ArrayList<>();
    public List<User> second = new ArrayList<>();
    public List<User> third = new ArrayList<>();
    public List<User> fourth = new ArrayList<>();

    private MyAdapter mAdapter;
    private ImageView addfriend;
    private List<TitleInfo> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentfriend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addfriend = getActivity().findViewById(R.id.addfriend);
        mElistview = getActivity().findViewById(R.id.mElistview);
        initList();
        initAdapter();
        initListenter();
    }

    private void initListenter() {

        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(getContext()).builder()
                        .setTitle("添加好友")
                        .setEditText("请输入昵称");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        User user = BmobUser.getCurrentUser(User.class);
                        BmobQuery<User> bmobQuery = new BmobQuery<>();
                        bmobQuery.addWhereEqualTo("nickname",myAlertInputDialog.getResult());
                        bmobQuery.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if (e == null){
                                    if (!list.isEmpty()){
                                        user.addUnique("third",list.get(0));

                                        myAlertInputDialog.dismiss();
                                    }else {
                                        Toast.makeText(getContext(), "昵称不存在！！", Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                user.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null)
                                            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        };
                        timer.schedule(timerTask,1000);

                        //Toast.makeText(getContext(), myAlertInputDialog.getResult(), Toast.LENGTH_SHORT).show();
                        //showMsg(myAlertInputDialog.getResult());
                        //myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
                        //showMsg("取消");
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();

            }
        });


        //子对象点击监听事件
        mElistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(getContext()).builder()
                        .setTitle("发送私信")
                        .setEditText("请输入内容");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User sender = BmobUser.getCurrentUser(User.class);
                        User receiver = mList.get(groupPosition).getInfo().get(childPosition);
                        Message message = new Message();
                        message.setContent(myAlertInputDialog.getResult());
                        message.setSender(sender);
                        message.setReceiver(receiver);
                        message.setSendernickname(sender.getNickname());
                        System.out.println("sssssssssssssssssssssssssss"+sender.getNickname());
                        message.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null){
                                    Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                                    myAlertInputDialog.dismiss();
                                }else{
                                    Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
                        //showMsg("取消");
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();


                Toast.makeText(getActivity(), mList.get(groupPosition).getInfo().get(childPosition).getNickname() + "", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //组对象点击监听事件
        mElistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if (e == null) {

                        } else {

                        }
                    }
                });
                initList();

                return false;//请务必返回false，否则分组不会展开
            }
        });
        //组对象判断分组监听事件
        mAdapter.setOnGroupExPanded(this);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mAdapter = new MyAdapter(mList, getContext());
        mElistview.setAdapter(mAdapter);
    }

    /**
     * 初始化数据源
     */
    private void initList() {
        mList.clear();
        first.clear();
        second.clear();
        third.clear();
        fourth.clear();
        User current = BmobUser.getCurrentUser(User.class);

        if (current.getFirst()!=null){
            first = current.getFirst();
        }else {
            first.add(current);
        }
        if (current.getSecond()!=null){
            second = current.getSecond();
        }
        if (current.getThird()!=null){
            third = current.getThird();
        }
        if (current.getFourth()!=null){
            fourth = current.getFourth();
        }


/*        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    //current.add("");
                    first.add(object.get(0));
                    second.add(object.get(0));
                    third.add(object.get(0));
                    fourth.add(object.get(0));
                } else {
                }
            }
        });*/

/*        Timer timer = new Timer();
        TimerTask timerTask=(new TimerTask() {
            @Override
            public void run() {
                current.setFirst(first);
                current.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            System.out.println("!!!!!!!!!!!!!!!!cgl");
                            System.out.println(current.getFirst().toArray());
                        }else {
                            System.out.println("eeeeeeeeeeeeeeee"+e);
                        }
                    }
                });
            }
        });
        timer.schedule(timerTask,2000);*/

        titleInfo1.setTitle("特别关心");
        titleInfo2.setTitle("我的家人");
        titleInfo3.setTitle("我的好友");
        titleInfo4.setTitle("陌生人");


        titleInfo1.setInfo(first);
        titleInfo2.setInfo(second);
        titleInfo3.setInfo(third);
        titleInfo4.setInfo(fourth);

        mList.add(titleInfo1);
        mList.add(titleInfo2);
        mList.add(titleInfo3);
        mList.add(titleInfo4);

    }


    /**
     * 监听是否关闭其他的组对象
     *
     * @param groupPostion
     */
    @Override
    public void onGroupExpanded(int groupPostion) {
        EListViewUtils utils = new EListViewUtils();
        utils.expandOnlyOne(groupPostion, mElistview);
    }
}
