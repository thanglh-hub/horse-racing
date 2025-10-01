package com.example.horseracing;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BetHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Spinner spinnerField;
    private Spinner spinnerDir;
    private final List<BetItem> items = new ArrayList<>();
    private BetHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_history);

        recyclerView = findViewById(R.id.recycler_history);
        spinnerField = findViewById(R.id.spinner_sort_field);
        spinnerDir = findViewById(R.id.spinner_sort_dir);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BetHistoryAdapter(items);
        recyclerView.setAdapter(adapter);

        setupSpinners();
        seedMockData();
        applySort();
    }

    private void setupSpinners() {
        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Time", "Amount"});
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerField.setAdapter(fieldAdapter);

        ArrayAdapter<String> dirAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Descending", "Ascending"});
        dirAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDir.setAdapter(dirAdapter);

        android.widget.AdapterView.OnItemSelectedListener listener = new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) { applySort(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        };
        spinnerField.setOnItemSelectedListener(listener);
        spinnerDir.setOnItemSelectedListener(listener);
    }

    private void seedMockData() {
        items.clear();
        items.add(new BetItem("01/10/2025 10:20", 50, 2, true));
        items.add(new BetItem("28/09/2025 21:05", 20, 1, false));
        items.add(new BetItem("30/09/2025 14:32", 80, 3, true));
        items.add(new BetItem("25/09/2025 09:12", 10, 2, false));
        items.add(new BetItem("29/09/2025 19:47", 45, 1, true));
    }

    private void applySort() {
        final boolean byAmount = spinnerField.getSelectedItemPosition() == 1;
        final boolean ascending = spinnerDir.getSelectedItemPosition() == 1;

        Comparator<BetItem> comparator;
        if (byAmount) {
            comparator = Comparator.comparingInt(o -> o.amount);
        } else {
            comparator = new Comparator<BetItem>() {
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                @Override public int compare(BetItem o1, BetItem o2) {
                    try {
                        Date d1 = sdf.parse(o1.time);
                        Date d2 = sdf.parse(o2.time);
                        if (d1 == null || d2 == null) return 0;
                        return d1.compareTo(d2);
                    } catch (ParseException e) {
                        return 0;
                    }
                }
            };
        }

        Collections.sort(items, comparator);
        if (!ascending) Collections.reverse(items);
        adapter.notifyDataSetChanged();
    }

    static class BetItem {
        final String time; 
        final int amount;
        final int horse;
        final boolean win;
        BetItem(String time, int amount, int horse, boolean win) {
            this.time = time; this.amount = amount; this.horse = horse; this.win = win;
        }
    }
}


