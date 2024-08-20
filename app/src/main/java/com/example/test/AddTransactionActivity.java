package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText amountEditText;
    private EditText descriptionEditText;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountEditText = findViewById(R.id.amount);
        descriptionEditText = findViewById(R.id.description);
        db = new DatabaseHelper(this);
    }

    public void addTransaction(View view) {
        String amount = amountEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (db.addTransaction(amount, description)) {
            Toast.makeText(this, "Transaction Added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to Add Transaction", Toast.LENGTH_SHORT).show();
        }
    }
}
