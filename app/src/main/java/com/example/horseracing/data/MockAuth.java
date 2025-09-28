package com.example.horseracing.data;

import java.util.HashMap;
import java.util.Map;

public class MockAuth {
    private static MockAuth instance;
    private final Map<String, String> userToPass = new HashMap<>();
    private String currentUser = null;

    private MockAuth() {
        userToPass.put("test", "123");
    }

    public static synchronized MockAuth getInstance() {
        if (instance == null) instance = new MockAuth();
        return instance;
    }

    public boolean login(String user, String pass) {
        // Demo mode: accept any non-empty username and password
        if (user != null && !user.trim().isEmpty() && pass != null && !pass.isEmpty()) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public boolean register(String user, String pass) {
        // Demo mode: always allow registration
        if (user != null && !user.trim().isEmpty() && pass != null && !pass.isEmpty()) {
            userToPass.put(user, pass);
            return true;
        }
        return false;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}


