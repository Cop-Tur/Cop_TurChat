package com.yywzs.cop_turchat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yywzs.cop_turchat.MainActivity;
import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.bean.User;
import com.yywzs.cop_turchat.fragment.FragmentMe;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Setting extends AppCompatActivity {

    private EditText nickname,telephone,area,password,oldpassword;
    private RadioButton man,girl;
    private Button save;
    private ImageView back;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nickname.getText().toString().trim();
                String tele = telephone.getText().toString().trim();
                String ar = area.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String oldpass = oldpassword.getText().toString().trim();
                Boolean isman = man.isChecked();
                if (!pass.isEmpty()&&!oldpass.isEmpty()){
                    BmobUser.updateCurrentUserPassword(oldpass, pass, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Toast.makeText(Setting.this, "", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Setting.this, "密码错误"+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                user.setNickname(name);
                user.setMobilePhoneNumber(tele);
                user.setArea(ar);
                if (isman){
                    user.setGender("男");
                }else {
                    user.setGender("女");
                }
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(Setting.this, "修改成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Setting.this,MainActivity.class));
                        } else {
                            Toast.makeText(Setting.this, "修改失败，请检查手机号是否正确", Toast.LENGTH_SHORT).show();
                            System.out.println("!!!!!!!!!!"+e);
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               // startActivity(new Intent(Setting.this, FragmentMe.class));
            }
        });
    }

    private void initData() {
        user = BmobUser.getCurrentUser(User.class);
        nickname.setText(user.getNickname().trim());
        area.setText(user.getArea()==null?"未设置":user.getArea().trim());
        telephone.setText(user.getMobilePhoneNumber()==null?"00000000000":user.getMobilePhoneNumber().trim());
        if (user.getGender().trim().equals("男")){
            girl.setChecked(false);
            man.setChecked(true);
        }else {
            man.setChecked(false);
            girl.setChecked(true);
        }
        nickname.setText(user.getNickname().trim());
        nickname.setText(user.getNickname().trim());

    }

    private void initView() {
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        nickname = findViewById(R.id.nickname);
        area = findViewById(R.id.area);
        telephone = findViewById(R.id.telephone);
        password = findViewById(R.id.password);
        man = findViewById(R.id.man);
        girl = findViewById(R.id.girl);
        oldpassword = findViewById(R.id.oldpassword);
    }
}
