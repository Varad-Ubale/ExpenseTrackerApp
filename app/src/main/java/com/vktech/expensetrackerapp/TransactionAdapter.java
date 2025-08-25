package com.vktech.expensetrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private ArrayList<TransactionModel> transactionList;
    private Context context;

    public TransactionAdapter(ArrayList<TransactionModel> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_row, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionModel transaction = transactionList.get(position);

        holder.textDate.setText(transaction.getDate());
        holder.textAmount.setText(String.valueOf(transaction.getAmount()));
        holder.textType.setText(transaction.getType());
        holder.textDescription.setText(transaction.getDescription());

        // Color logic
        if (transaction.getType().equalsIgnoreCase("Income")) {
            holder.rowLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light));
        } else if (transaction.getType().equalsIgnoreCase("Expense")) {
            holder.rowLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
        } else {
            holder.rowLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textDate, textAmount, textType, textDescription;
        LinearLayout rowLayout;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textAmount = itemView.findViewById(R.id.textAmount);
            textType = itemView.findViewById(R.id.textType);
            textDescription = itemView.findViewById(R.id.textDescription);
            rowLayout = itemView.findViewById(R.id.rowLayout); // matches updated XML
        }
    }
}
