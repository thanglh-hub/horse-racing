package com.example.horseracing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BetHistory {
    public static class BetItem {
        final String time;
        final int amount;
        final int horse;
        final boolean win;
        final boolean draw; 
        
        BetItem(String time, int amount, int horse, boolean win) {
            this.time = time; 
            this.amount = amount; 
            this.horse = horse; 
            this.win = win;
            this.draw = false;
        }
        
        BetItem(String time, int amount, int horse, boolean win, boolean draw) {
            this.time = time; 
            this.amount = amount; 
            this.horse = horse; 
            this.win = win;
            this.draw = draw;
        }
    }

    public static final List<BetItem> historyList = new ArrayList<>();
    private static  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    
    public static void AddBetResult(int amount, int selectedHorse, boolean isWin, boolean isDraw) {
        String currentTime = dateFormat.format(new Date());
        BetItem item = new BetItem(currentTime, amount, selectedHorse, isWin, isDraw);
        historyList.add(0, item);
    }
    public static List<BetItem> getHistoryList(){
        return new ArrayList<>(historyList);
    }
}
