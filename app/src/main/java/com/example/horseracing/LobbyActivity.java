package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horseracing.data.GameState;

public class LobbyActivity extends AppCompatActivity {
    private TextView balanceText;
    private CheckBox cbHorse1, cbHorse2, cbHorse3;
    private EditText etBetH1, etBetH2, etBetH3;
    private Button startBtn;
    private Button resetBtn;
    private Button logoutBtn;
    private Button addFundsBtn;
    private Button historyBtn;
    private Button achievementsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Đảm bảo nhạc nền phát trong Lobby
        com.example.horseracing.data.AudioPlayer.playBgm(this);

        balanceText = findViewById(R.id.text_balance);
        startBtn = findViewById(R.id.btn_start);
        resetBtn = findViewById(R.id.btn_reset);
        logoutBtn = findViewById(R.id.btn_logout);
        addFundsBtn = findViewById(R.id.btn_add_funds);
        historyBtn = findViewById(R.id.btn_history);
        achievementsBtn = findViewById(R.id.btn_achievements);

        cbHorse1 = findViewById(R.id.cb_horse1);
        cbHorse2 = findViewById(R.id.cb_horse2);
        cbHorse3 = findViewById(R.id.cb_horse3);
        etBetH1 = findViewById(R.id.et_bet_h1);
        etBetH2 = findViewById(R.id.et_bet_h2);
        etBetH3 = findViewById(R.id.et_bet_h3);

        updateBalance();

        // RadioGroup sẽ tự động handle việc chọn radio button

        startBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            doStart();
        });
        resetBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            doReset();
        });
        logoutBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleLogout();
        });

        addFundsBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            Intent intent = new Intent(this, AddFundsActivity.class);
            startActivity(intent);
        });

        if (historyBtn != null) {
            historyBtn.setOnClickListener(v -> {
                com.example.horseracing.data.AudioPlayer.playButtonClick(this);
                Intent i = new Intent(this, BetHistoryActivity.class);
                startActivity(i);
            });
        }
        if (achievementsBtn != null) {
            achievementsBtn.setOnClickListener(v -> {
                com.example.horseracing.data.AudioPlayer.playButtonClick(this);
                Intent i = new Intent(this, AchievementsActivity.class);
                startActivity(i);
            });
        }
    }

    private void updateBalance() {
        int balance = GameState.getInstance().getBalance();
        balanceText.setText(getString(R.string.label_balance, balance));

        // Disable/enable Start button based on balance
        startBtn.setEnabled(balance > 0);
        if (balance <= 0) {
            startBtn.setAlpha(0.5f); // Làm mờ button khi disabled
        } else {
            startBtn.setAlpha(1.0f); // Bình thường khi enabled
        }
    }

    private void doReset() {
        cbHorse1.setChecked(false);
        cbHorse2.setChecked(false);
        cbHorse3.setChecked(false);
        etBetH1.setText("");
        etBetH2.setText("");
        etBetH3.setText("");
    }
    private void doStart() {
        int totalBet = 0;

        int betH1 = cbHorse1.isChecked() ? parseBet(etBetH1.getText().toString()) : 0;
        int betH2 = cbHorse2.isChecked() ? parseBet(etBetH2.getText().toString()) : 0;
        int betH3 = cbHorse3.isChecked() ? parseBet(etBetH3.getText().toString()) : 0;

        totalBet = betH1 + betH2 + betH3;

        if (totalBet <= 0) {
            Toast.makeText(this, "Bạn phải đặt cược hợp lệ cho ít nhất 1 ngựa!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbHorse1.isChecked() && !cbHorse2.isChecked() && !cbHorse3.isChecked()) {
            Toast.makeText(this, "Bạn phải chọn ít nhất 1 ngựa!", Toast.LENGTH_SHORT).show();
            return;
        }

        int balance = GameState.getInstance().getBalance();
        if (totalBet > balance) {
            Toast.makeText(this, "Bạn không đủ tiền để đặt cược!", Toast.LENGTH_SHORT).show();
            return;
        }

        GameState.getInstance().addBalance(-totalBet);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("bet_H1", betH1);
        i.putExtra("bet_H2", betH2);
        i.putExtra("bet_H3", betH3);
        startActivity(i);
    }

    private int parseBet(String betStr) {
        if (betStr == null || betStr.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(betStr.trim());
        }
        catch (Exception ex){
            return 0;
        }
    }

    private void handleLogout() {
        // Hiển thị dialog xác nhận logout
        new android.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(getString(R.string.msg_logout_confirm))
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Dừng nhạc nền
                    com.example.horseracing.data.AudioPlayer.stopBgm();

                    // Clear game state nếu cần
                    // GameState.getInstance().reset(); // Uncomment nếu muốn reset balance

                    // Chuyển về LoginActivity và clear back stack
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBalance(); // Cập nhật balance và trạng thái button khi quay lại
    }
}


