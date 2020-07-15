package com.yywzs.cop_turchat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yywzs.cop_turchat.R;
import scut.carson_ho.searchview.*;

public class Search extends AppCompatActivity {
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // 3. 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setFocusable(true);

        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
