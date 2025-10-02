package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        int betH1 = getIntent().getIntExtra("bet_H1", 0);
        int betH2 = getIntent().getIntExtra("bet_H2", 0);
        int betH3 = getIntent().getIntExtra("bet_H3", 0);
        int[] ranking = getIntent().getIntArrayExtra("ranking");

        if (ranking == null || ranking.length < 3) {
            resultText.setText("Lỗi: Không có kết quả!");

            resultText.setText(getString(R.string.label_balance, GameState.getInstance().getBalance()));
            playAgain.setEnabled(false);
            playAgain.setAlpha(0.5f);
            backLobby.setOnClickListener(v -> {
                com.example.horseracing.data.AudioPlayer.playButtonClick(ResultActivity.this);
                com.example.horseracing.data.AudioPlayer.playBgm(ResultActivity.this);
                startActivity(new Intent(ResultActivity.this, LobbyActivity.class));
                finish();
            });
            return;
        }
        int first = ranking[0];
        int second = ranking[1];
        int third = ranking[2];

        int totalBet = betH1 + betH2 + betH3;

        int payout = 0;

        if (betH1 > 0) {
            if (first == 1) payout += betH1 * 2;
            else if (second == 1) payout += betH1;
        }
        if (betH2 > 0) {
            if (first == 2) payout += betH2 * 2;
            else if (second == 2) payout += betH2;
        }
        if (betH3 > 0) {
            if (first == 3) payout += betH3 * 2;
            else if (second == 3) payout += betH3;
        }

        GameState.getInstance().addBalance(payout);

        int netChange = payout - totalBet;
        StringBuilder sb = new StringBuilder();
        sb.append("1st: Ngựa ").append(first)
                .append("\n2nd: Ngựa ").append(second)
                .append("\n3rd: Ngựa ").append(third)
                .append("\n\n");

        if (netChange > 0) {
            sb.append("Bạn thắng: +").append(netChange);
            resultText.setText((sb.toString()));
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
        } else if (netChange == 0) {
            sb.append("Hòa: Không mất không được");
            resultText.setText(sb.toString());
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
        } else {
            sb.append("Bạn thua: Mất ").append(Math.abs(netChange));
            resultText.setText(sb.toString());
            com.example.horseracing.data.AudioPlayer.playGameOver(this);
        }

        balanceText.setText(getString(R.string.label_balance, GameState.getInstance().getBalance()));

        boolean canPlayAgain = totalBet > 0 && GameState.getInstance().getBalance() >= totalBet;
        playAgain.setEnabled(canPlayAgain);

        playAgain.setOnClickListener(v ->
        {
            com.example.horseracing.data.AudioPlayer.playButtonClick(ResultActivity.this);
            int curBalance = GameState.getInstance().getBalance();
            if (totalBet <= 0) {
                Toast.makeText(ResultActivity.this, "Không có cược để chơi lại", Toast.LENGTH_SHORT).show();
                return;
            }
            if (curBalance < totalBet) {
                Toast.makeText(ResultActivity.this, "Không đủ tiền để chơi lại. Quay về lobby để nạp thêm.", Toast.LENGTH_SHORT).show();
                com.example.horseracing.data.AudioPlayer.playBgm(ResultActivity.this);
                startActivity(new Intent(ResultActivity.this, LobbyActivity.class));
                finish();
                return;
            }
            GameState.getInstance().addBalance(-totalBet);

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("bet_H1", betH1);
            i.putExtra("bet_H2", betH2);
            i.putExtra("bet_H3", betH3);
            startActivity(i);
            finish();
        });

        backLobby.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(ResultActivity.this);
            // Khởi động lại nhạc nền khi quay về Lobby
            com.example.horseracing.data.AudioPlayer.playBgm(ResultActivity.this);
            startActivity(new Intent(this, LobbyActivity.class));
            finish();
        });
    }
}


