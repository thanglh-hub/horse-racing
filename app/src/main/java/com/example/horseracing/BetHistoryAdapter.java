package com.example.horseracing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BetHistoryAdapter extends RecyclerView.Adapter<BetHistoryAdapter.VH> {
    private final List<BetHistoryActivity.BetItem> data;
    public BetHistoryAdapter(List<BetHistoryActivity.BetItem> data) { this.data = data; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bet_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        BetHistoryActivity.BetItem item = data.get(position);
        holder.time.setText(item.time);
        holder.amount.setText((item.win ? "+ " : "- ") + item.amount);
        holder.detail.setText("Horse " + item.horse + " • Amount " + item.amount + " • " + (item.win ? "Win" : "Lose"));
        holder.amount.setTextColor(holder.amount.getResources().getColor(item.win ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final TextView time; final TextView amount; final TextView detail;
        VH(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.text_time);
            amount = itemView.findViewById(R.id.text_amount);
            detail = itemView.findViewById(R.id.text_detail);
        }
    }
}


