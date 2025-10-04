package com.example.horseracing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BetHistoryAdapter extends BaseAdapter {
    private final List<BetHistoryActivity.BetItem> data;
    private final LayoutInflater inflater;

    public BetHistoryAdapter(Context context, List<BetHistoryActivity.BetItem> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_bet_history, parent, false);
            holder = new ViewHolder();
            holder.time = convertView.findViewById(R.id.text_time);
            holder.amount = convertView.findViewById(R.id.text_amount);
            holder.detail = convertView.findViewById(R.id.text_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BetHistoryActivity.BetItem item = data.get(position);
        holder.time.setText(item.time);

        if (item.win) {
            holder.amount.setText("+" + item.amount);
            holder.amount.setTextColor(holder.amount.getResources().getColor(android.R.color.holo_green_dark));
        } else if (item.draw) {
            holder.amount.setText("0");
            holder.amount.setTextColor(holder.amount.getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            holder.amount.setText("-" + item.amount);
            holder.amount.setTextColor(holder.amount.getResources().getColor(android.R.color.holo_red_dark));
        }
        
        String result = item.win ? "Win" : (item.draw ? "Draw" : "Lose");
        holder.detail.setText("Horse " + item.horse + " • Bet " + item.amount + "$ • " + result);

        return convertView;
    }

    static class ViewHolder {
        TextView time;
        TextView amount;
        TextView detail;
    }
}


