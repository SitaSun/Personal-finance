package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void addTransaction(View view) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        startActivity(intent);
    }

    public void viewBudget(View view) {
        Intent intent = new Intent(this, Bak_BudgetActivity.class);
        startActivity(intent);
    }

    public void backupRestore(View view) {
        //Intent intent = new Intent(this, BackupRestoreActivity.class);
        //startActivity(intent);
    }
}
