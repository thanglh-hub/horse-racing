package com.example.horseracing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.VH> {
    private final List<AchievementsActivity.Achievement> data;
    public AchievementsAdapter(List<AchievementsActivity.Achievement> data) { this.data = data; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        AchievementsActivity.Achievement item = data.get(position);
        holder.title.setText(item.title);
        holder.desc.setText(item.desc);
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final TextView title; final TextView desc;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            desc = itemView.findViewById(R.id.text_desc);
        }
    }
}


