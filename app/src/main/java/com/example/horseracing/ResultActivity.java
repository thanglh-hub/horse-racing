package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horseracing.data.GameState;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Tắt nhạc nền ở màn hình kết quả
        com.example.horseracing.data.AudioPlayer.stopBgm();

        TextView resultText = findViewById(R.id.text_result);
        TextView balanceText = findViewById(R.id.text_balance);
        Button playAgain = findViewById(R.id.btn_play_again);
        Button backLobby = findViewById(R.id.btn_back_lobby);

        int selected = getIntent().getIntExtra("selectedHorse", -1);
        int bet = getIntent().getIntExtra("bet", 0);
        String betType = getIntent().getStringExtra("betType");

        int winner = getIntent().getIntExtra("winner", -1);
        int second = getIntent().getIntExtra("second", -1);
        int third = getIntent().getIntExtra("third", -1);

        boolean win = false;
        double multiplier = 0;
        int rank = -1; // thứ hạng ngựa chọn

        if ("winner".equals(betType)) {
            if (selected == winner) {
                win = true;
                multiplier = 2.0;
                rank = 1;
            }
        } else if ("top3".equals(betType)) {
            if (selected == winner) {
                win = true;
                multiplier = 1.2;
                rank = 1;
            } else if (selected == second) {
                win = true;
                multiplier = 1.2;
                rank = 2;
            } else if (selected == third) {
                win = true;
                multiplier = 1.2;
                rank = 3;
            }
        }

        if (win) {
            int reward = (int) (bet * multiplier);
            GameState.getInstance().addBalance(reward);
            resultText.setText("Bạn thắng! Ngựa số " + selected + " về hạng " + rank + ". +" + reward + "$");
            com.example.horseracing.data.AudioPlayer.playWinner(this);
        } else {
            resultText.setText("Bạn thua cược! Ngựa bạn chọn không về đúng hạng.");
            com.example.horseracing.data.AudioPlayer.playGameOver(this);
        }

        balanceText.setText(getString(R.string.label_balance, GameState.getInstance().getBalance()));

        playAgain.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("selectedHorse", selected);
            i.putExtra("bet", bet);
            i.putExtra("betType", betType);
            startActivity(i);
            finish();
        });

        backLobby.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            // Khởi động lại nhạc nền khi quay về Lobby
            com.example.horseracing.data.AudioPlayer.playBgm(this);
            startActivity(new Intent(this, LobbyActivity.class));
            finish();
        });
    }
}
