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
        int winner = getIntent().getIntExtra("winner", -1);

        if (winner == selected) {
            GameState.getInstance().addBalance(bet);
            resultText.setText(getString(R.string.msg_win, winner));
            // Chỉ phát âm thanh thắng
            com.example.horseracing.data.AudioPlayer.playWinner(this);
        } else {
            GameState.getInstance().addBalance(-bet);
            resultText.setText(getString(R.string.msg_lose, winner));
            // Chỉ phát âm thanh thua
            com.example.horseracing.data.AudioPlayer.playGameOver(this);
        }
        balanceText.setText(getString(R.string.label_balance, GameState.getInstance().getBalance()));

        playAgain.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("selectedHorse", selected);
            i.putExtra("bet", bet);
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


