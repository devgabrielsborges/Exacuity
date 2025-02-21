package com.borges.exacuity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borges.exacuity.R;
import com.borges.exacuity.utils.ExhibitionUtils;
import com.borges.exacuity.utils.SettingsUtils;

public class ConfigGridAdapter extends RecyclerView.Adapter<ConfigGridAdapter.ViewHolder> implements View.OnKeyListener {
    private final Context context;
    private final int[] iconIds;
    private final String[] topTexts;
    private final String[] bottomTexts;
    private final String PREFS_NAME = "AppSettings";

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
        // attach key listener on each item view
        view.setOnKeyListener(this);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.configIcon.setImageResource(iconIds[position]);
        holder.topText.setText(topTexts[position]);

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String defaultBottomText = bottomTexts[position];
        String storedBottomText = prefs.getString("config_bottom_text_" + position, defaultBottomText);

        holder.bottomText.setText(storedBottomText);

        holder.itemView.setTag(holder);

        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            float scale = hasFocus ? 1.1f : 1.0f;
            v.animate().scaleX(scale).scaleY(scale).setDuration(200).start();
            int color = hasFocus ?
                    context.getColor(android.R.color.white) :
                    context.getColor(R.color.title_color);

            holder.topText.setTextColor(color);
            holder.bottomText.setTextColor(color);
            holder.configIcon.setColorFilter(color);
            v.setElevation(hasFocus ? 4f : 0f);
        });
        
        // Toggle edit mode and show/hide arrow container on click.
        holder.itemView.setOnClickListener(v -> {
            holder.isEditing = !holder.isEditing;
            if (holder.isEditing) {
                holder.arrowLeft.setVisibility(View.VISIBLE);
                holder.arrowRight.setVisibility(View.VISIBLE);
            } else {
                holder.arrowLeft.setVisibility(View.GONE);
                holder.arrowRight.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconIds.length;
    }

    /**
     * onKey handles DPAD left/right events to adjust the numeric part of the bottom text,
     * but only when the current ViewHolder is in edit mode.
     */
    @SuppressLint("DefaultLocale")
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // Retrieve the holder from the view's tag.
        ViewHolder holder = (ViewHolder) v.getTag();
        if (holder == null || !holder.isEditing) {
            return false;
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int position = holder.getAdapterPosition();
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            boolean handled = false;
            String newText;

            switch (position) {
                case 0: { // Update distance in meters (delta = 0.1, suffix " m")
                    String rawDistance = bottomTexts[0].replace(" m", "").trim();
                    float defaultDistance = Float.parseFloat(rawDistance);
                    float distance = prefs.getFloat("distance", defaultDistance);
                    float delta = 0.1f;
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && distance > 0.2) {
                        distance -= delta;
                        handled = true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        distance += delta;
                        handled = true;
                    }
                    if (handled) {
                        newText = String.format("%.1f m", distance);
                        prefs.edit().putFloat("distance", distance).apply();
                        prefs.edit().putString("config_bottom_text_0", newText).apply();
                        holder.bottomText.setText(newText);
                    }
                    break;
                }
                case 1: { // Update height in mm (delta = 1, suffix " mm")
                    String defaultStr = bottomTexts[1].trim();
                    int defaultHeight;
                    if (defaultStr.endsWith("mm")) {
                        String rawHeight = defaultStr.replace(" mm", "").trim();
                        defaultHeight = Integer.parseInt(rawHeight);
                    } else if (defaultStr.endsWith("m")) {
                        String rawHeight = defaultStr.replace(" m", "").trim();
                        float meters = Float.parseFloat(rawHeight);
                        defaultHeight = (int) (meters * 1000);
                    } else {
                        defaultHeight = 87;
                    }

                    // Get the current height from SharedPreferences.
                    int height = prefs.getInt("e_height", defaultHeight);
                    int delta = 1;
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && height > 2) {
                        height -= delta;
                        handled = true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        height += delta;
                        handled = true;
                    }
                    if (handled) {
                        newText = height + " mm";
                        prefs.edit().putInt("e_height", height).apply();
                        prefs.edit().putString("config_bottom_text_1", newText).apply();
                        holder.bottomText.setText(newText);
                    }
                    break;
                }
                case 2: { // Update acuity index with delta = 1 to choose from acuitys array
                    int index = prefs.getInt("acuity_index", 0);
                    int delta = 1;
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        index = Math.max(0, index - delta);
                        handled = true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        index = Math.min(ExhibitionUtils.exhibitionAcuities.length - 1, index + delta);
                        handled = true;
                    }
                    if (handled) {
                        newText = ExhibitionUtils.exhibitionAcuities[index];
                        prefs.edit().putInt("acuity_index", index).apply();
                        prefs.edit().putString("config_bottom_text_2", newText).apply();
                        holder.bottomText.setText(newText);
                    }
                    break;
                }
                case 3: { // Update chart index with delta = 1 to choose from charts array
                    int index = prefs.getInt("chart_index", 0);
                    int delta = 1;
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        index = Math.max(0, index - delta);
                        handled = true;
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        index = Math.min(SettingsUtils.charts.length - 1, index + delta);
                        handled = true;
                    }
                    if (handled) {
                        newText = SettingsUtils.charts[index];
                        prefs.edit().putInt("chart_index", index).apply();
                        prefs.edit().putString("config_bottom_text_3", newText).apply();
                        holder.bottomText.setText(newText);
                    }
                    break;
                }
                default:
                    break;
            }

            return handled;
        }
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView configIcon;
        final TextView topText;
        final TextView bottomText;
        final View arrowContainer;
        final View arrowLeft;
        final View arrowRight;
        boolean isEditing = false;
    
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            configIcon = itemView.findViewById(R.id.config_icon);
            topText = itemView.findViewById(R.id.config_top_text);
            bottomText = itemView.findViewById(R.id.config_bottom_text);
            arrowContainer = itemView.findViewById(R.id.arrow_container);
            arrowLeft = itemView.findViewById(R.id.arrow_left);
            arrowRight = itemView.findViewById(R.id.arrow_right);
        }
    }
}