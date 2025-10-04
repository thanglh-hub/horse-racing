package com.example.horseracing;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends AppCompatActivity {
    private ListView listViewAhievements;
    private AchievementsAdapter adapter;
    private List<Achievement> achievements;
    AchievementManager manager = AchievementManager.getInstance();
    String currentDate = manager.getCurrentDate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        listViewAhievements = findViewById(R.id.lv_achievements);

        setData();
        adapter = new AchievementsAdapter(this, achievements);
        listViewAhievements.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btnBackToLobby);
        btnBack.setOnClickListener(v -> finish());
    }

    private void setData() {
        achievements = new ArrayList<>();
        achievements.add(new Achievement(
                "New Driver",
                "Win your first race",
                manager.hasFirstWin(),
                manager.hasFirstWin() ? currentDate : null
        ));

        achievements.add(new Achievement(
                "Rich Gamer",
                "Win 100$ ",
                manager.isRichGamer(),
                manager.isRichGamer() ? currentDate : null
        ));

        achievements.add(new Achievement(
                "Unstoppable",
                "Win 3 races",
                manager.hasLuckyStreak(),
                manager.hasLuckyStreak() ? currentDate : null
        ));
    }

    public class Achievement {
        public final String title;
        public final String description;
        final boolean isUnlocked;
        final String unlockedDate;
        public Achievement(String title, String description, boolean isUnlocked, String unlockedDate) {
            this.title = title;
            this.description = description;
            this.isUnlocked = isUnlocked;
            this.unlockedDate = unlockedDate;
        }
    }
}


