package com.vktech.expensetrackerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextAmount, editTextDescription;
    private RadioButton radioExpense, radioEarning;
    private TextView textViewBalance;
    private RecyclerView recyclerViewHistory;
    private TransactionAdapter adapter;
    private ArrayList<TransactionModel> transactionList;
    private int balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextDescription = findViewById(R.id.editTextDescription);
        radioExpense = findViewById(R.id.radioExpense);
        radioEarning = findViewById(R.id.radioEarning); // fixed name
        textViewBalance = findViewById(R.id.textViewBalance);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        // Setup RecyclerView
        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(transactionList, this);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        // Handle confirm button click
        findViewById(R.id.buttonConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = editTextAmount.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (!amountStr.isEmpty() && (radioExpense.isChecked() || radioEarning.isChecked())) {
                    int amount = Integer.parseInt(amountStr);
                    String type = radioExpense.isChecked() ? "Expense" : "Income";

                    // Update balance
                    balance += type.equals("Expense") ? -amount : amount;
                    textViewBalance.setText("Balance: Rs. " + balance);

                    // Add transaction
                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    transactionList.add(0, new TransactionModel(0, date, amount, type, description));
                    adapter.notifyItemInserted(0);
                    recyclerViewHistory.scrollToPosition(0);

                    // Clear inputs
                    editTextAmount.setText("");
                    editTextDescription.setText("");
                    radioExpense.setChecked(false);
                    radioEarning.setChecked(false);
                }
            }
        });
    }
}
