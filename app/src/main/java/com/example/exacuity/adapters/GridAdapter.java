package com.example.exacuity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exacuity.R;
import com.example.exacuity.activities.DaltonismActivity;
import com.example.exacuity.activities.ExhibitionActivity;
import com.example.exacuity.activities.ImageActivity;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private final Context context;
    private final int[] iconIds; // drawable resource IDs

    public GridAdapter(Context context, int[] iconIds) {
        this.context = context;
        this.iconIds = iconIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int iconId = iconIds[position];
        holder.iconImage.setImageResource(iconId);

        boolean shouldTint = shouldTintIcon(iconId);

        if (shouldTint) {
            int tintColor = context.getColor(R.color.grid_icon_color);
            holder.iconImage.setColorFilter(tintColor);
        }

        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            float scale = hasFocus ? 1.1f : 1.0f;
            v.animate().scaleX(scale).scaleY(scale).setDuration(200).start();

            float elevation = hasFocus ? 8f : 0f;
            v.setElevation(elevation);

            if (shouldTint) {
                int color = hasFocus ? context.getColor(android.R.color.white) : context.getColor(R.color.title_color);
                holder.iconImage.setColorFilter(color);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (iconIds[position] == R.drawable.icon_g11){
                Intent daltonismIntent = new Intent(context, DaltonismActivity.class);
                context.startActivity(daltonismIntent);
            } else if (iconIds[position] == R.drawable.icon_g12) {
                Intent amslerIntent = new Intent(context, ImageActivity.class);
                amslerIntent.putExtra("image_source", R.drawable.amsler);
                context.startActivity(amslerIntent);
            } else if (iconIds[position] == R.drawable.icon_g15) {
                Intent dialIntent = new Intent(context, ImageActivity.class);
                dialIntent.putExtra("image_source", R.drawable.dial);
                context.startActivity(dialIntent);
            } else {
                Intent intentExhibition = new Intent(context, ExhibitionActivity.class);
                intentExhibition.putExtra("iconResId", iconIds[position]);
                context.startActivity(intentExhibition);
            }
        });
    }

    private boolean shouldTintIcon(int iconId) {
        return iconId != R.drawable.icon_g11 && iconId != R.drawable.icon_g14;
    }

    @Override
    public int getItemCount() {
        return iconIds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon_image);
        }
    }
}