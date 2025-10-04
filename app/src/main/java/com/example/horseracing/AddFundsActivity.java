package com.example.horseracing;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.horseracing.data.GameState;

public class AddFundsActivity extends AppCompatActivity {
    
    private EditText editAmount;
    private Button btnConfirm;
    private Button btnCancel;
    private Button btnViewDepositHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);

        editAmount = findViewById(R.id.edit_amount);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);
        btnViewDepositHistory = findViewById(R.id.btn_view_deposit_history);

        btnConfirm.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleAddFunds();
        });

        btnCancel.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            finish();
        });

        btnViewDepositHistory.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            startActivity(new android.content.Intent(this, DepositHistoryActivity.class));
        });
    }

    private void handleAddFunds() {
        String amountStr = editAmount.getText().toString().trim();
        
        if (amountStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_invalid_amount), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int amount = Integer.parseInt(amountStr);
            
            if (amount <= 0) {
                Toast.makeText(this, getString(R.string.msg_invalid_amount), Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm tiền vào GameState
            GameState gameState = GameState.getInstance();
            gameState.addBalance(amount);

            // Lưu lịch sử nạp tiền
            DepositHistoryActivity.addDepositHistory(this, amount);

            // Hiển thị thông báo thành công
            Toast.makeText(this, getString(R.string.msg_funds_added, amount), Toast.LENGTH_LONG).show();
            // Đóng activity và quay về Lobby
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.msg_invalid_amount), Toast.LENGTH_SHORT).show();
        }
    }
}
