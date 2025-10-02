package com.example.horseracing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class AchievementsAdapter extends BaseAdapter {

    static class AchievementViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;
        TextView createdAt;
        View overlayLocked;
        ImageView iconLock;
    }
    private final List<AchievementsActivity.Achievement> data;

    private final  LayoutInflater inflater;
    public AchievementsAdapter (Context context, List<AchievementsActivity.Achievement> data) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        AchievementViewHolder achievementViewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_achievement, viewGroup, false);
            achievementViewHolder = new AchievementViewHolder();
            achievementViewHolder.icon = view.findViewById(R.id.icon_achieve);
            achievementViewHolder.title = view.findViewById(R.id.text_title);
            achievementViewHolder.desc = view.findViewById(R.id.text_desc);
            achievementViewHolder.createdAt = view.findViewById(R.id.text_createdAt);
            achievementViewHolder.overlayLocked = view.findViewById(R.id.overlay_locked);
            achievementViewHolder.iconLock = view.findViewById(R.id.icon_lock);
            view.setTag(achievementViewHolder);
        } else {
            achievementViewHolder = (AchievementViewHolder) view.getTag();
        }

        AchievementsActivity.Achievement item = data.get(position);
        achievementViewHolder.title.setText(item.title);
        achievementViewHolder.desc.setText(item.description);

        if (item.isUnlocked){
            achievementViewHolder.createdAt.setText("Achieved on " + item.unlockedDate);
            achievementViewHolder.overlayLocked.setVisibility(View.GONE);
            achievementViewHolder.createdAt.setVisibility(View.VISIBLE);
            achievementViewHolder.iconLock.setVisibility(View.GONE);
        } else {
            achievementViewHolder.createdAt.setVisibility(View.GONE);
            achievementViewHolder.overlayLocked.setVisibility(View.VISIBLE);
            achievementViewHolder.iconLock.setVisibility(View.VISIBLE);
        }
        return view;
    }
}


