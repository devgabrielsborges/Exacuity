package com.example.exacuity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConfigGridAdapter extends RecyclerView.Adapter<ConfigGridAdapter.ViewHolder> {
    private final Context context;
    private final int[] iconIds; // drawable resource IDs
    private final String[] topTexts;
    private final String[] bottomTexts;
    
    
    public ConfigGridAdapter(Context context, int[] iconIds, String[] topTexts, String[] bottomTexts) {
        this.context = context;
        this.iconIds = iconIds;
        this.topTexts = topTexts;
        this.bottomTexts = bottomTexts;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_config_item, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.configIcon.setImageResource(iconIds[position]);
    holder.topText.setText(topTexts[position]);
    
    SharedPreferences prefs = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
    String defaultBottomText = bottomTexts[position];
    String bottomText = prefs.getString("config_bottom_text_" + position, defaultBottomText);
    holder.bottomText.setText(bottomText);
    
    // Smooth scale animation on focus
    holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
        float scale = hasFocus ? 1.1f : 1.0f;
        v.animate().scaleX(scale).scaleY(scale).setDuration(200).start();

        int color;
        if (hasFocus) {
            color = context.getResources().getColor(android.R.color.white);
        } else {
            color = context.getResources().getColor(R.color.title_color);
        }
        
        holder.topText.setTextColor(color);
        holder.bottomText.setTextColor(color);
        holder.configIcon.setColorFilter(color);

        float elevation = hasFocus ? 4f : 0f;
        v.setElevation(elevation);
    });
    
    }
    
    @Override
    public int getItemCount() {
        return iconIds.length;
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView configIcon;
        TextView topText;
        TextView bottomText;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            configIcon = itemView.findViewById(R.id.config_icon);
            topText = itemView.findViewById(R.id.config_top_text);
            bottomText = itemView.findViewById(R.id.config_bottom_text);
        }
    }
}