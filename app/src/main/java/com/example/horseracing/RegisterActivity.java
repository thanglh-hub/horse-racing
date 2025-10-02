package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horseracing.data.MockAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button registerButton;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Tắt nhạc nền cho trang register
        com.example.horseracing.data.AudioPlayer.stopBgm();
        
        usernameInput = findViewById(R.id.input_username);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        confirmPasswordInput = findViewById(R.id.input_confirm_password);
        registerButton = findViewById(R.id.button_register);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
        });

        registerButton.setOnClickListener(v -> { 
            com.example.horseracing.data.AudioPlayer.playButtonClick(this); 
            doRegister(); 
        });
    }

    private void doRegister() {
        String user = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String pass = passwordInput.getText().toString();
        String confirmPass = confirmPasswordInput.getText().toString();
        
        // Kiểm tra tất cả trường đã được điền
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(this, getString(R.string.msg_fill_all), Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Kiểm tra email hợp lệ
        if (!isValidEmail(email)) {
            Toast.makeText(this, getString(R.string.msg_invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp nhau
        if (!pass.equals(confirmPass)) {
            Toast.makeText(this, getString(R.string.msg_password_mismatch), Toast.LENGTH_SHORT).show();
            return;
        }
        
        boolean ok = MockAuth.getInstance().register(user, pass);
        if (ok) {
            Toast.makeText(this, getString(R.string.msg_register_ok), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.msg_register_fail), Toast.LENGTH_SHORT).show();
        }
    }
    
    private boolean isValidEmail(String email) {
        // Kiểm tra email cơ bản: có @ và có ít nhất 1 dấu chấm sau @
        return email != null && 
               email.contains("@") && 
               email.indexOf("@") > 0 && 
               email.lastIndexOf(".") > email.indexOf("@") && 
               email.lastIndexOf(".") < email.length() - 1;
    }
}


