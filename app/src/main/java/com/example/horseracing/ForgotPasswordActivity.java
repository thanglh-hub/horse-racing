package com.example.horseracing;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    // UI Components
    private LinearLayout stepEmail, stepOtp, stepPassword;
    private EditText emailInput, otpInput, newPasswordInput, confirmNewPasswordInput;
    private Button sendOtpBtn, verifyOtpBtn, resetPasswordBtn;
    
    // Current step (1, 2, or 3)
    private int currentStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        // Tắt nhạc nền cho trang forgot password
        com.example.horseracing.data.AudioPlayer.stopBgm();
        
        initViews();
        setupClickListeners();
        showStep(1); // Bắt đầu với bước 1
    }

    private void initViews() {
        // LinearLayouts cho các bước
        stepEmail = findViewById(R.id.step_email);
        stepOtp = findViewById(R.id.step_otp);
        stepPassword = findViewById(R.id.step_password);
        
        // EditTexts
        emailInput = findViewById(R.id.input_email);
        otpInput = findViewById(R.id.input_otp);
        newPasswordInput = findViewById(R.id.input_new_password);
        confirmNewPasswordInput = findViewById(R.id.input_confirm_new_password);
        
        // Buttons
        sendOtpBtn = findViewById(R.id.btn_send_otp);
        verifyOtpBtn = findViewById(R.id.btn_verify_otp);
        resetPasswordBtn = findViewById(R.id.btn_reset_password);
    }

    private void setupClickListeners() {
        sendOtpBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleSendOtp();
        });
        
        verifyOtpBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleVerifyOtp();
        });
        
        resetPasswordBtn.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            handleResetPassword();
        });
    }

    private void showStep(int step) {
        // Ẩn tất cả các bước
        stepEmail.setVisibility(View.GONE);
        stepOtp.setVisibility(View.GONE);
        stepPassword.setVisibility(View.GONE);
        
        // Hiển thị bước hiện tại
        switch (step) {
            case 1:
                stepEmail.setVisibility(View.VISIBLE);
                break;
            case 2:
                stepOtp.setVisibility(View.VISIBLE);
                break;
            case 3:
                stepPassword.setVisibility(View.VISIBLE);
                break;
        }
        currentStep = step;
    }

    private void handleSendOtp() {
        String email = emailInput.getText().toString().trim();
        
        // Demo mode: chấp nhận bất kỳ email nào không rỗng
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Demo: luôn gửi OTP thành công
        Toast.makeText(this, getString(R.string.msg_otp_sent), Toast.LENGTH_SHORT).show();
        showStep(2); // Chuyển sang bước 2
    }

    private void handleVerifyOtp() {
        String otp = otpInput.getText().toString().trim();
        
        // Demo mode: chấp nhận bất kỳ OTP nào không rỗng
        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Demo: luôn verify OTP thành công
        Toast.makeText(this, getString(R.string.msg_otp_verified), Toast.LENGTH_SHORT).show();
        showStep(3); // Chuyển sang bước 3
    }

    private void handleResetPassword() {
        String newPass = newPasswordInput.getText().toString();
        String confirmPass = confirmNewPasswordInput.getText().toString();
        
        // Kiểm tra tất cả trường đã được điền
        if (TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Kiểm tra password và confirm password có khớp nhau
        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, getString(R.string.msg_new_password_mismatch), Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Demo: luôn reset password thành công
        Toast.makeText(this, getString(R.string.msg_password_reset), Toast.LENGTH_SHORT).show();
        finish(); // Quay về màn hình login
    }
}


