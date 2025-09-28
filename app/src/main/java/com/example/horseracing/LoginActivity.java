package com.example.horseracing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.horseracing.data.MockAuth;
import com.example.horseracing.data.GameState;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView registerLink;
    private TextView forgotLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Tắt nhạc nền cho trang login
        com.example.horseracing.data.AudioPlayer.stopBgm();

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_login);
        registerLink = findViewById(R.id.link_register);
        forgotLink = findViewById(R.id.link_forgot);

        loginButton.setOnClickListener(v -> { 
            com.example.horseracing.data.AudioPlayer.playButtonClick(this); 
            handleLogin(); 
        });
        registerLink.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            startActivity(new Intent(this, RegisterActivity.class));
        });
        forgotLink.setOnClickListener(v -> {
            com.example.horseracing.data.AudioPlayer.playButtonClick(this);
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // keep bgm for whole app; do not stop here
    }

    private void handleLogin() {
        String user = usernameInput.getText().toString().trim();
        String pass = passwordInput.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, getString(R.string.msg_fill_all), Toast.LENGTH_SHORT).show();
            return;
        }
        boolean ok = MockAuth.getInstance().login(user, pass);
        if (ok) {
            GameState.getInstance().ensureInitialBalance();
            // Phát nhạc nền khi login thành công và chuyển vào Lobby
            com.example.horseracing.data.AudioPlayer.playBgm(this);
            startActivity(new Intent(this, LobbyActivity.class));
            finish();
        } else {
            Toast.makeText(this, getString(R.string.msg_login_fail), Toast.LENGTH_SHORT).show();
        }
    }
}


