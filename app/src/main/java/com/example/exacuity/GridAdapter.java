package com.example.exacuity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private final Context context;
    private final String[] symbols;

    public GridAdapter(Context context, String[] symbols) {
        this.context = context;
        this.symbols = symbols;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(symbols[position]);
        holder.itemView.setFocusable(true);
        holder.itemView.setFocusableInTouchMode(true);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExhibitionActivity.class);
            intent.putExtra("symbol", symbols[position]); // Optional: Pass data to the activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return symbols.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.grid_text);
        }
    }
}
