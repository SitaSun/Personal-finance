package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MySettingsActivity extends AppCompatActivity {
    Context mCotext;
    private static final String DATABASE_NAME = "finance.db";
    private static final String BACKUP_FOLDER_PATH = Environment.getExternalStorageDirectory().getPath() + "/MyFinanceAppBackup/";
    private static final String TAG = "MySettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysettings);

    }
    public void backupData(View view) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            //if (sd.canWrite()) {finance.db
            String currentDBPath = "/data/data/" + getPackageName() + "/databases/" + DATABASE_NAME;
            String backupDBPath = BACKUP_FOLDER_PATH + DATABASE_NAME;
            Log.d(TAG,"xiaoting backupData currentDBPath="+currentDBPath+",backupDBPath="+backupDBPath);
            File currentDB = new File(data, currentDBPath);
            File backupDBFilePath = new File(BACKUP_FOLDER_PATH);
            File backupDB = new File(backupDBPath);
            if (!backupDBFilePath.getParentFile().exists()) {
                Log.d(TAG,"xiaoting backupData backupDBFilePath");
                backupDBFilePath.getParentFile().mkdirs();
            }
            if (!backupDB.getParentFile().exists()) {
                backupDB.getParentFile().mkdirs();
            }

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(this, "Backup Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Database does not exist", Toast.LENGTH_SHORT).show();
            }
            //}
        } catch (Exception e) {
            Toast.makeText(this, "Backup Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void restoreData(View view) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            //if (sd.canWrite()) {
            String currentDBPath = "/data/data/" + getPackageName() + "/databases/" + DATABASE_NAME;
            String backupDBPath = BACKUP_FOLDER_PATH + DATABASE_NAME;
            Log.d(TAG,"xiaoting restoreData currentDBPath="+currentDBPath+",backupDBPath="+backupDBPath);
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(backupDBPath);

            if (backupDB.exists()) {
                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(this, "Restore Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Backup file does not exist", Toast.LENGTH_SHORT).show();
            }
            //}
        } catch (Exception e) {
            Toast.makeText(this, "Restore Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void budgetManger(View view){
        startActivity(new Intent(this,BudgetActivity.class));
    }
}