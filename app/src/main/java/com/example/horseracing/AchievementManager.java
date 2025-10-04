package com.example.horseracing;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AchievementManager {
    private static AchievementManager instance;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    private boolean hasFirstWin = false;
    private boolean isRichGamer = false;
    private boolean hasLuckyStreak = false;

    private int totalBetAmount = 0;
    private int currentWinStreak = 0;

    private AchievementManager() {}

    public static AchievementManager getInstance() {
        if (instance == null) {
            instance = new AchievementManager();
        }
        return instance;
    }

    public void onWin(int betAmount) {
        totalBetAmount += betAmount;
        currentWinStreak++;
        if (!hasFirstWin) {
            hasFirstWin = true;
        }
        if (!isRichGamer && totalBetAmount >= 100) {
            isRichGamer = true;
        }
        if (!hasLuckyStreak && currentWinStreak >= 3) {
            hasLuckyStreak = true;
        }
    }


    public boolean hasFirstWin() {
        return hasFirstWin;
    }

    public boolean isRichGamer() {
        return isRichGamer;
    }

    public boolean hasLuckyStreak() {
        return hasLuckyStreak;
    }

    public String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
