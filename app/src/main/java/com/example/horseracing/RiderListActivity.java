package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class RiderListActivity extends AppCompatActivity {

    private LinearLayout riderContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_list);

        riderContainer = findViewById(R.id.riderContainer);

        // Back về Lobby
        Button btnBack = findViewById(R.id.btnBackLobby);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(RiderListActivity.this, LobbyActivity.class));
            finish();
        });

        // Fake data
        ArrayList<HashMap<String, Object>> riderList = new ArrayList<>();

        HashMap<String, Object> rider1 = new HashMap<>();
        rider1.put("name", "Nguyen Van A");
        rider1.put("age", 26);
        rider1.put("races", 15);
        rider1.put("wins", 9);
        rider1.put("losses", 6);
        rider1.put("imageUrl", "https://i.pravatar.cc/150?img=1");
        riderList.add(rider1);

        HashMap<String, Object> rider2 = new HashMap<>();
        rider2.put("name", "Tran Van B");
        rider2.put("age", 30);
        rider2.put("races", 20);
        rider2.put("wins", 12);
        rider2.put("losses", 8);
        rider2.put("imageUrl", "https://i.pravatar.cc/150?img=2");
        riderList.add(rider2);

        HashMap<String, Object> rider3 = new HashMap<>();
        rider3.put("name", "Le Thi C");
        rider3.put("age", 22);
        rider3.put("races", 10);
        rider3.put("wins", 4);
        rider3.put("losses", 6);
        rider3.put("imageUrl", "https://i.pravatar.cc/150?img=3");
        riderList.add(rider3);

        // Render từng rider
        for (HashMap<String, Object> rider : riderList) {
            addRiderView(rider);
        }
    }

    private void addRiderView(HashMap<String, Object> rider) {
        // Layout cha
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(24, 24, 24, 24);
        itemLayout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        // Avatar
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(200, 200);
        imgParams.setMargins(0, 0, 32, 0);
        imageView.setLayoutParams(imgParams);
        Glide.with(this).load(rider.get("imageUrl")).circleCrop().into(imageView);

        // Thông tin rider
        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setOrientation(LinearLayout.VERTICAL);

        TextView name = new TextView(this);
        name.setText((String) rider.get("name"));
        name.setTextSize(20f);
        name.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView age = new TextView(this);
        age.setText("Age: " + rider.get("age"));

        TextView stats = new TextView(this);
        stats.setText("Races: " + rider.get("races")
                + " | Wins: " + rider.get("wins")
                + " | Losses: " + rider.get("losses"));

        textLayout.addView(name);
        textLayout.addView(age);
        textLayout.addView(stats);

        // Gắn image + text
        itemLayout.addView(imageView);
        itemLayout.addView(textLayout);

        // Add vào container
        riderContainer.addView(itemLayout);
    }
}
