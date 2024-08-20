package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private List<Transaction> transactions;
    private LayoutInflater inflater;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.transaction_item, parent, false);
        }

        TextView amount = convertView.findViewById(R.id.amount);
        TextView description = convertView.findViewById(R.id.description);

        Transaction transaction = transactions.get(position);
        amount.setText(transaction.getAmount()+"");
        description.setText(transaction.getDescription());

        return convertView;
    }
}
