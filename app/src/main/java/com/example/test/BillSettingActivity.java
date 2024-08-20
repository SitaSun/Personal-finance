package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class BillSettingActivity extends AppCompatActivity {
    Button mAbout,mVersion;
    Context mCotext;
    private static final String TAG = "BillSettingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_setting);
        mCotext = this;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 显示返回按钮
            actionBar.setHomeButtonEnabled(true); // 设置返回按钮可点击
            actionBar.show();
        }
        mAbout = findViewById(R.id.about);
        mVersion = findViewById(R.id.version);
        //mTvAbout.setText("关于：个人理财");
        mVersion.setText("版本号："+getVersionName(mCotext));

    }
    public void mySetting(View view){
        Intent intent = new Intent(this, MySettingsActivity.class);
        startActivity(intent);
    }

    public static String getVersionName(Context context) {
    //获取包管理器
    PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }
    public void budgetAddManger(View view){
        startActivity(new Intent(this,BudgetActivity.class));
    }
}