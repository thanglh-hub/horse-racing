package com.example.horseracing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BetHistory {
    public static class BetItem {
        public final String time;
        public  final int amount;
        public final int horse;
        public final boolean win;
        public BetItem(String time, int amount, int horse, boolean win) {
            this.time = time; this.amount = amount; this.horse = horse; this.win = win;
        }
    }

    public static final List<BetItem> historyList = new ArrayList<>();
    private static  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public static void AddBetResult(int amount, int selectedHorse, int winner) {
        String currentTime = dateFormat.format(new Date());
        boolean isWin = (selectedHorse == winner);
        BetItem item = new BetItem(currentTime, amount, selectedHorse, isWin);
        historyList.add(0,item);
    }

    public static List<BetItem> getHistoryList(){
        return new ArrayList<>(historyList);
    }
}
