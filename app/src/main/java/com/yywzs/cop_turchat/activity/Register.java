package com.yywzs.cop_turchat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yywzs.cop_turchat.R;
import com.yywzs.cop_turchat.bean.User;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText nickname;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.nickname);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User registerUser = new User();
                registerUser.setUsername(username.getText().toString().trim());
                registerUser.setPassword(password.getText().toString().trim());
                registerUser.setNickname(nickname.getText().toString().trim());
                registerUser.setArea("未设置");
               // registerUser.setMobilePhoneNumber("11100000000");
                registerUser.setGender("男");

                if (username.getText().toString().equals("")){
                    Toast.makeText(Register.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().equals("")){
                    Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Timer timer = new Timer();
                                timer.schedule(timerTask,1500);
                            }else {
                                Toast.makeText(Register.this, "用户名或昵称已被注册", Toast.LENGTH_SHORT).show();
                                System.out.println("!!!!!!!!!!!!!"+e);
                            }
                        }
                    });
                }
            }
        });
    }
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            startActivity(new Intent(Register.this, Login.class));
        }
    };

}
