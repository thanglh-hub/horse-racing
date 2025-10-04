package com.example.horseracing;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class RaceListActivity extends AppCompatActivity {

    private LinearLayout raceContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_list);

        Button btnBack = findViewById(R.id.btnBackLobbyRace);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(RaceListActivity.this, LobbyActivity.class);
            startActivity(i);
            finish();
        });

        raceContainer = findViewById(R.id.raceContainer);

        // Fake data
        ArrayList<HashMap<String, String>> raceList = new ArrayList<>();

        HashMap<String, String> race1 = new HashMap<>();
        race1.put("name", "Vietnam Grand Derby");
        race1.put("location", "Hanoi");
        race1.put("date", "2025-10-10");
        race1.put("imageUrl", "https://picsum.photos/600/300?random=1");
        raceList.add(race1);

        HashMap<String, String> race2 = new HashMap<>();
        race2.put("name", "Saigon Classic Cup");
        race2.put("location", "Ho Chi Minh City");
        race2.put("date", "2025-11-05");
        race2.put("imageUrl", "https://picsum.photos/600/300?random=2");
        raceList.add(race2);

        HashMap<String, String> race3 = new HashMap<>();
        race3.put("name", "Hue Royal Horse Race");
        race3.put("location", "Hue");
        race3.put("date", "2025-12-01");
        race3.put("imageUrl", "https://picsum.photos/600/300?random=3");
        raceList.add(race3);

        // Render từng race
        for (HashMap<String, String> race : raceList) {
            addRaceCard(race);
        }
    }

    private void addRaceCard(HashMap<String, String> race) {
        // Tạo CardView
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 32);
        cardView.setLayoutParams(params);
        cardView.setRadius(20f);
        cardView.setCardElevation(10f);
        cardView.setUseCompatPadding(true);

        // Layout bên trong CardView
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(16, 16, 16, 16);

        // Ảnh banner
        ImageView img = new ImageView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                400
        ));
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(race.get("imageUrl")).into(img);

        // Tên giải đua
        TextView name = new TextView(this);
        name.setText(race.get("name"));
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextColor(Color.BLACK);
        name.setPadding(0, 12, 0, 6);

        // Location
        TextView location = new TextView(this);
        location.setText("Location: " + race.get("location"));
        location.setTextSize(14);
        location.setTextColor(Color.DKGRAY);

        // Date
        TextView date = new TextView(this);
        date.setText("Date: " + race.get("date"));
        date.setTextSize(14);
        date.setTextColor(Color.DKGRAY);

        // Add view vào layout
        cardLayout.addView(img);
        cardLayout.addView(name);
        cardLayout.addView(location);
        cardLayout.addView(date);

        // Gắn layout vào CardView
        cardView.addView(cardLayout);

        // Thêm CardView vào container
        raceContainer.addView(cardView);
    }
}
