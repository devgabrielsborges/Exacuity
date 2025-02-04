package com.example.exacuity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

public class ConfigurationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        RecyclerView recyclerViewConfig = findViewById(R.id.recyclerViewConfig);
        recyclerViewConfig.setLayoutManager(new GridLayoutManager(this, 4)); // 4 columns

        int[] iconIds = {
            R.drawable.distance_config_icon,
            R.drawable.e_height_config_icon,
            R.drawable.visual_acuity_config_icon,
            R.drawable.initial_chart_config_icon
        };
        
        String[] topTexts = {
            "Distancia",
            "Tela",
            "Acuidade Visual",
            "Tabela Inicial"
        };
        
        // Default bottomTexts
        String[] bottomTexts = {
            "4.0 m",
            "1.8 m",
            "20/20",
            "Chart 1"
        };
        
        ConfigGridAdapter adapter = new ConfigGridAdapter(this, iconIds, topTexts, bottomTexts);
        recyclerViewConfig.setAdapter(adapter);
    }
}