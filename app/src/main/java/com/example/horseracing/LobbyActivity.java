package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horseracing.data.GameState;

public class LobbyActivity extends AppCompatActivity {
    private TextView balanceText;
    private RadioGroup radioGroupHorses;
    private RadioButton rbHorse1;
    private RadioButton rbHorse2;
    private RadioButton rbHorse3;
    private SeekBar betSeek;
    private TextView betValue;
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
        radioGroupHorses = findViewById(R.id.radio_group_horses);
        rbHorse1 = findViewById(R.id.rb_horse1);
        rbHorse2 = findViewById(R.id.rb_horse2);
        rbHorse3 = findViewById(R.id.rb_horse3);
        betSeek = findViewById(R.id.seek_bet);
        betValue = findViewById(R.id.text_bet_value);
        startBtn = findViewById(R.id.btn_start);
        resetBtn = findViewById(R.id.btn_reset);
        logoutBtn = findViewById(R.id.btn_logout);
        addFundsBtn = findViewById(R.id.btn_add_funds);
        historyBtn = findViewById(R.id.btn_history);
        achievementsBtn = findViewById(R.id.btn_achievements);

        updateBalance();

        betSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                betValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        betSeek.setProgress(10);

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
        radioGroupHorses.clearCheck();
        betSeek.setProgress(10);
        betValue.setText("10");
    }

    private void doStart() {
        int selected = getSelectedHorse();
        if (selected == -1) {
            Toast.makeText(this, getString(R.string.msg_pick_horse), Toast.LENGTH_SHORT).show();
            return;
        }
        
        int bet = betSeek.getProgress();
        int currentBalance = GameState.getInstance().getBalance();
        
        // Kiểm tra balance = 0
        if (currentBalance <= 0) {
            Toast.makeText(this, getString(R.string.msg_no_balance), Toast.LENGTH_LONG).show();
            return;
        }
        
        // Kiểm tra bet amount > balance
        if (bet > currentBalance) {
            Toast.makeText(this, getString(R.string.msg_bet_exceeds_balance), Toast.LENGTH_LONG).show();
            return;
        }
        
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("selectedHorse", selected);
        i.putExtra("bet", bet);
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


