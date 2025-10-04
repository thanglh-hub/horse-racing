package com.example.horseracing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DepositHistoryActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "transaction_history_prefs";
    private static final String KEY_HISTORY = "transaction_history";

    // Call this to add a transaction record
    public static void addTransactionHistory(Context context, int amount, String type) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String history = prefs.getString(KEY_HISTORY, "");
        String newRecord = System.currentTimeMillis() + "," + amount + "," + type;
        history = history.isEmpty() ? newRecord : history + ";" + newRecord;
        prefs.edit().putString(KEY_HISTORY, history).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_history);
        ListView listView = findViewById(R.id.list_deposit_history);
        ArrayList<String> displayList = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String history = prefs.getString(KEY_HISTORY, "");
        if (!history.isEmpty()) {
            String[] records = history.split(";");
            for (String record : records) {
                String[] parts = record.split(",");
                if (parts.length == 3) {
                    long timestamp = Long.parseLong(parts[0]);
                    int amount = Integer.parseInt(parts[1]);
                    String type = parts[2];
                    String date = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date(timestamp)).toString();
                    String label = type.equals("deposit") ? "Deposit" : "Withdraw";
                    displayList.add(date + " - " + label + ": " + amount + "$");
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        // Add close button functionality
        android.widget.Button btnClose = findViewById(R.id.btn_close_history);
        btnClose.setOnClickListener(v -> finish());
    }
}
