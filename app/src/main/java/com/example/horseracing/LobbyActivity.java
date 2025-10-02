package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horseracing.data.GameState;

public class LobbyActivity extends AppCompatActivity {
    private TextView balanceText, betValue, textOdds;
    private RadioGroup radioGroupHorses, radioGroupBetType;
    private RadioButton rbHorse1, rbHorse2, rbHorse3;
    private RadioButton rbWinner, rbTop3, rbCancel;
    private SeekBar betSeek;
    private Button startBtn, resetBtn, logoutBtn, addFundsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Play background music
        com.example.horseracing.data.AudioPlayer.playBgm(this);

        // --- Ánh xạ UI ---
        balanceText = findViewById(R.id.text_balance);
        betValue = findViewById(R.id.text_bet_value);
        textOdds = findViewById(R.id.text_odds);

        radioGroupHorses = findViewById(R.id.radio_group_horses);
        rbHorse1 = findViewById(R.id.rb_horse1);
        rbHorse2 = findViewById(R.id.rb_horse2);
        rbHorse3 = findViewById(R.id.rb_horse3);

        radioGroupBetType = findViewById(R.id.radio_group_bet_type);
        rbWinner = findViewById(R.id.rb_winner);
        rbTop3 = findViewById(R.id.rb_top3);
        rbCancel = findViewById(R.id.rb_cancel);

        betSeek = findViewById(R.id.seek_bet);
        startBtn = findViewById(R.id.btn_start);
        resetBtn = findViewById(R.id.btn_reset);
        logoutBtn = findViewById(R.id.btn_logout);
        addFundsBtn = findViewById(R.id.btn_add_funds);

        // --- Cập nhật số dư ---
        updateBalance();

        // --- SeekBar để chọn tiền cược ---
        betSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                betValue.setText(String.valueOf(progress));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        betSeek.setProgress(10);

        // --- Chọn loại cược và hiển thị tỉ lệ ---
        radioGroupBetType.setOnCheckedChangeListener((group, checkedId) -> {
            String odds = "-";
            if (checkedId == R.id.rb_winner) {
                odds = "x2.0";
            } else if (checkedId == R.id.rb_top3) {
                odds = "x1.2";
            } else if (checkedId == R.id.rb_cancel) {
                odds = "0 (Hủy)";
            }
            textOdds.setText("Tỉ lệ cược: " + odds);
        });

        // --- Button action ---
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
    }

    private void updateBalance() {
        int balance = GameState.getInstance().getBalance();
        balanceText.setText(getString(R.string.label_balance, balance));

        // Disable Start nếu không có tiền
        startBtn.setEnabled(balance > 0);
        startBtn.setAlpha(balance > 0 ? 1.0f : 0.5f);
    }

    private void doReset() {
        radioGroupHorses.clearCheck();
        radioGroupBetType.check(R.id.rb_winner); // Reset về Winner
        betSeek.setProgress(10);
        betValue.setText("10");
        textOdds.setText("Tỉ lệ cược: x2.0");
    }

    private void doStart() {
        int selectedHorse = getSelectedHorse();
        if (selectedHorse == -1) {
            Toast.makeText(this, getString(R.string.msg_pick_horse), Toast.LENGTH_SHORT).show();
            return;
        }

        int bet = betSeek.getProgress();
        int currentBalance = GameState.getInstance().getBalance();

        if (currentBalance <= 0) {
            Toast.makeText(this, getString(R.string.msg_no_balance), Toast.LENGTH_LONG).show();
            return;
        }

        if (bet > currentBalance) {
            Toast.makeText(this, getString(R.string.msg_bet_exceeds_balance), Toast.LENGTH_LONG).show();
            return;
        }

        // --- Xử lý loại cược ---
        int betTypeId = radioGroupBetType.getCheckedRadioButtonId();
        String betType = "winner"; // default
        if (betTypeId == R.id.rb_top3) {
            betType = "top3";
        } else if (betTypeId == R.id.rb_cancel) {
            doReset();
            Toast.makeText(this, "Bạn đã hủy cược!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Trừ tiền trong balance
        GameState.getInstance().addBalance(-bet);

        // Chuyển qua màn hình đua
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("selectedHorse", selectedHorse);
        i.putExtra("bet", bet);
        i.putExtra("betType", betType);
        startActivity(i);
    }

    private int getSelectedHorse() {
        int checkedId = radioGroupHorses.getCheckedRadioButtonId();
        if (checkedId == R.id.rb_horse1) return 1;
        if (checkedId == R.id.rb_horse2) return 2;
        if (checkedId == R.id.rb_horse3) return 3;
        return -1;
    }

    private void handleLogout() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(getString(R.string.msg_logout_confirm))
                .setPositiveButton("Yes", (dialog, which) -> {
                    com.example.horseracing.data.AudioPlayer.stopBgm();
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
        updateBalance();
    }
}
