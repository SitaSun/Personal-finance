package com.example.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test.bean.Budget;
import java.util.Calendar;

public class BudgetActivity extends AppCompatActivity {

    private EditText etBudgetAmount;
    private Button btnSaveBudget;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        etBudgetAmount = findViewById(R.id.etBudgetAmount);
        btnSaveBudget = findViewById(R.id.btnSaveBudget);
        dbHelper = new DatabaseHelper(this);

        // 获取当前月份的预算
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        Budget currentBudget = dbHelper.getBudget(month, year);
        if (currentBudget != null) {
            etBudgetAmount.setText(String.valueOf(currentBudget.getAmount()));
        }

        btnSaveBudget.setOnClickListener(v -> {
            double amount = Double.parseDouble(etBudgetAmount.getText().toString());
            Budget currentSaveBudget = dbHelper.getBudget(month, year);
            if (currentSaveBudget == null) {
                currentSaveBudget = new Budget(0, amount, month, year);
                dbHelper.addBudget(currentSaveBudget);
            } else {
                currentSaveBudget.setAmount(amount);
                dbHelper.updateBudget(currentSaveBudget);
            }
            Toast.makeText(this, "预算已保存", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
