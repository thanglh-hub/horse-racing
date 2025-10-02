package com.example.horseracing;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.horseracing.data.GameState;

public class AddFundsActivity extends AppCompatActivity {

    private EditText editAmount;
    private Button btnConfirm, btnCancel, btnWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);

        editAmount = findViewById(R.id.edit_amount);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);
        btnWithdraw = findViewById(R.id.btn_withdraw);

        btnConfirm.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleAddFunds();
        });

        btnWithdraw.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleWithdrawFunds();
        });

        btnCancel.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            finish();
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

            GameState gameState = GameState.getInstance();
            gameState.addBalance(amount);

            Toast.makeText(this, getString(R.string.msg_funds_added, amount), Toast.LENGTH_LONG).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.msg_invalid_amount), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleWithdrawFunds() {
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

            GameState gameState = GameState.getInstance();
            if (amount > gameState.getBalance()) {
                Toast.makeText(this, getString(R.string.msg_insufficient_balance), Toast.LENGTH_LONG).show();
                return;
            }

            gameState.addBalance(-amount); // Trừ tiền
            Toast.makeText(this, getString(R.string.msg_funds_withdrawn, amount), Toast.LENGTH_LONG).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.msg_invalid_amount), Toast.LENGTH_SHORT).show();
        }
    }
}
