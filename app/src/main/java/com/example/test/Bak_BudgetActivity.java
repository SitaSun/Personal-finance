package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Bak_BudgetActivity extends AppCompatActivity {

    private TextView totalBudget;
    private TextView totalIncome;
    private TextView totalExpense;
    private ListView transactionList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bak_activity_budget);

        totalBudget = findViewById(R.id.total_budget);
        totalIncome = findViewById(R.id.total_income);
        totalExpense = findViewById(R.id.total_expense);
        transactionList = findViewById(R.id.transaction_list);
        db = new DatabaseHelper(this);

        loadBudgetData();
    }

    private void loadBudgetData() {
        int budget = db.getTotalBudget();
        int income = db.getTotalIncome();
        int expense = db.getTotalExpense();

        totalBudget.setText(String.valueOf(budget));
        totalIncome.setText(String.valueOf(income));
        totalExpense.setText(String.valueOf(expense));

        List<Transaction> transactions = db.getAllTransactions();
        TransactionAdapter adapter = new TransactionAdapter(this, transactions);
        transactionList.setAdapter(adapter);
    }

    public void setBudget(View view) {
        // Placeholder for setting budget functionality
        Toast.makeText(this, "Set Budget Clicked", Toast.LENGTH_SHORT).show();
    }
}





