package com.example.horseracing;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AchievementsAdapter adapter;
    private final List<Achievement> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        recyclerView = findViewById(R.id.recycler_achievements);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AchievementsAdapter(items);
        recyclerView.setAdapter(adapter);

        seedMock();
    }

    private void seedMock() {
        items.clear();
        items.add(new Achievement("New Driver", "Win your first race"));
        items.add(new Achievement("Winning Streak", "Win 3 races in a row"));
        items.add(new Achievement("Millionaire", "Balance reaches 500"));
        adapter.notifyDataSetChanged();
    }

    static class Achievement {
        final String title; final String desc;
        Achievement(String title, String desc) { this.title = title; this.desc = desc; }
    }
}


