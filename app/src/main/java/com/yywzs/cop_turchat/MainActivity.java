package com.yywzs.cop_turchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.next.easynavigation.view.EasyNavigationBar;
import com.yywzs.cop_turchat.activity.MoreWindow;
import com.yywzs.cop_turchat.fragment.FragmentFriend;
import com.yywzs.cop_turchat.fragment.FragmentHome;
import com.yywzs.cop_turchat.fragment.FragmentMe;
import com.yywzs.cop_turchat.fragment.FragmentMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EasyNavigationBar navigationBar;
    CardView idContainer;
    MoreWindow mMoreWindow;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        navigationBar = findViewById(R.id.navigationBar);
        idContainer = findViewById(R.id.idContainer);
        mMoreWindow = new MoreWindow(this);
        mMoreWindow.init(idContainer);
        initFrag();
        initBar();
        makeStatusBarTransparent(this);
    }

    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initFrag() {
        fragments.add(new FragmentHome());
        fragments.add(new FragmentFriend());
        fragments.add(new FragmentMessage());
        fragments.add(new FragmentMe());
    }

    private void showMoreWindow() {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(this);
            mMoreWindow.init(idContainer);
        }

        mMoreWindow.showMoreWindow(idContainer );
    }

    private void initBar() {
        String[] tabText = {"话题","朋友","消息","我的"};
        //未选中
        int[] normalIcon = {R.drawable.ic_house,R.drawable.ic_people,R.drawable.ic_bell,R.drawable.ic_person};
        //选中时icon
        int[] selectIcon = {R.drawable.ic_house_fill,R.drawable.ic_people_fill,R.drawable.ic_bell_fill,R.drawable.ic_person_fill};


        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .iconSize(20)     //Tab图标大小
//                .tabTextSize(10)   //Tab文字大小
//                .tabTextTop(2)     //Tab文字距Tab图标的距离
                .normalTextColor(Color.parseColor("#666666"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#333333"))   //Tab选中时字体颜色
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)  //同 ImageView的ScaleType
                .navigationBackground(Color.parseColor("#E7E5E5"))   //导航栏背景色
                .smoothScroll(true)  //点击Tab  Viewpager切换是否有动画
                .canScroll(false)    //Viewpager能否左右滑动
                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
                .centerImageRes(R.drawable.ic_plus_circle_fill)
                .centerIconSize(46)    //中间加号图片的大小
                .centerLayoutHeight(60)   //包含加号的布局高度 背景透明  所以加号看起来突出一块
                .navigationHeight(60)  //导航栏高度
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
                    @Override
                    public boolean onCenterTabSelectEvent(View view) {
                        showMoreWindow();
                        return false;
                    }
                })
                .build();
    }
}
