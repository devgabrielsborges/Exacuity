package com.example.exacuity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.iconImage.setImageResource(iconIds[position]);

        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            float scale = hasFocus ? 1.1f : 1.0f;
            v.animate().scaleX(scale).scaleY(scale).setDuration(200).start();

            float elevation = hasFocus ? 4f : 0f;
            v.setElevation(elevation);

            int color = hasFocus ? context.getResources().getColor(android.R.color.white) : context.getResources().getColor(R.color.title_color);

            holder.iconImage.setColorFilter(color);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExhibitionActivity.class);
            intent.putExtra("iconResId", iconIds[position]); 
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return iconIds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon_image);
        }
    }
}