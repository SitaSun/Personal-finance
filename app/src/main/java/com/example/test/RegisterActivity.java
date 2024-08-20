package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private DatabaseHelper db;
    private static final String TAG = "RegisterActivity";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        db = new DatabaseHelper(this);
    }

    public void register(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        //boolean checkUsernameExists = db.checkUsernameExists(username);
        //Log.d(TAG,"checkUsernameExists="+checkUsernameExists);<注释掉了暂时用不到这个逻辑>
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(mContext,"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (db.createUser(username, password)) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            Toast.makeText(mContext, "用户名已经存在，请进行登录", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

