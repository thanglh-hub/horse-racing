package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SeekBar horse1;
    private SeekBar horse2;
    private SeekBar horse3;
    private android.widget.ImageView img1;
    private android.widget.ImageView img2;
    private android.widget.ImageView img3;
    private TextView statusText;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();
    private int selectedHorse;
    private int bet;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horse1 = findViewById(R.id.seek_h1);
        horse2 = findViewById(R.id.seek_h2);
        horse3 = findViewById(R.id.seek_h3);
        img1 = findViewById(R.id.img_h1);
        img2 = findViewById(R.id.img_h2);
        img3 = findViewById(R.id.img_h3);
        statusText = findViewById(R.id.text_status);

        selectedHorse = getIntent().getIntExtra("selectedHorse", -1);
        bet = getIntent().getIntExtra("bet", 0);

        // Tắt nhạc nền và chỉ phát âm thanh đua ngựa
        com.example.horseracing.data.AudioPlayer.stopBgm();
        com.example.horseracing.data.AudioPlayer.playHorseRacing(this);
        
        startRaceLoop();
    }

    private void startRaceLoop() {
        horse1.setProgress(0);
        horse2.setProgress(0);
        horse3.setProgress(0);
        finished = false;

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (finished) return;
                int p1 = horse1.getProgress() + random.nextInt(5);
                int p2 = horse2.getProgress() + random.nextInt(5);
                int p3 = horse3.getProgress() + random.nextInt(5);
                horse1.setProgress(Math.min(p1, horse1.getMax()));
                horse2.setProgress(Math.min(p2, horse2.getMax()));
                horse3.setProgress(Math.min(p3, horse3.getMax()));

                // sync horse images moving horizontally according to progress
                float width = ((android.view.View) statusText.getParent()).getWidth();
                float maxMove = width - img1.getWidth() - 32f;
                img1.setTranslationX(maxMove * horse1.getProgress() / (float) horse1.getMax());
                img2.setTranslationX(maxMove * horse2.getProgress() / (float) horse2.getMax());
                img3.setTranslationX(maxMove * horse3.getProgress() / (float) horse3.getMax());

                int winner = checkWinner();
                if (winner != -1) {
                    finished = true;
                    statusText.setText(getString(R.string.msg_horse_won, winner));
                    // Không phát âm thanh ở đây, sẽ phát ở ResultActivity
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("selectedHorse", selectedHorse);
                    i.putExtra("bet", bet);
                    i.putExtra("winner", winner);
                    startActivity(i);
                    finish();
                } else {
                    handler.postDelayed(this, 100);
                }
            }
        });
    }

    private int checkWinner() {
        if (horse1.getProgress() >= horse1.getMax()) return 1;
        if (horse2.getProgress() >= horse2.getMax()) return 2;
        if (horse3.getProgress() >= horse3.getMax()) return 3;
        return -1;
    }
}