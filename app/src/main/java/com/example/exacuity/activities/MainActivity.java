package com.example.exacuity.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exacuity.R;
import com.example.exacuity.adapters.GridAdapter;
import com.example.exacuity.utils.SettingsUtils;

// TODO: create e-size exhibition

public class MainActivity extends AppCompatActivity { // Changed superclass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SettingsUtils.initializeDefaultSettings(this);
        setTitleName();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5)); // 5 columns

        GridAdapter adapter = getGridAdapter();
        recyclerView.setAdapter(adapter);

        Button buttonSettings = findViewById(R.id.buttonSettings);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonSettings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConfigurationsActivity.class);
            startActivity(intent);
        });

        buttonReset.setOnClickListener(view -> showResetConfirmationDialog());
    }

    private @NonNull GridAdapter getGridAdapter() {
        int[] iconIds = {
                R.drawable.icon_g1,
                R.drawable.icon_g2,
                R.drawable.icon_g3,
                R.drawable.icon_g4,
                R.drawable.icon_g5,
                R.drawable.icon_g6,
                R.drawable.icon_g7,
                R.drawable.icon_g8,
                R.drawable.icon_g9,
                R.drawable.icon_g10,
                R.drawable.icon_g11,
                R.drawable.icon_g12,
                R.drawable.icon_g13,
                R.drawable.icon_g14,
                R.drawable.icon_g15,

        };

        return new GridAdapter(this, iconIds);
    }

    private void setTitleName() {
        EditText topText = findViewById(R.id.top_text);
        SharedPreferences prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        String savedTitle = prefs.getString("top_text_value", getString(R.string.app_name));
        topText.setText(savedTitle);

        topText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String newValue = topText.getText().toString();
                prefs.edit().putString("top_text_value", newValue).apply();
            }
        });
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Resetar aplicativo")
                .setMessage("Deseja resetar o aplicativo?")
                .setPositiveButton("Confirmar", (dialog, which) -> SettingsUtils.performSettingsReset(MainActivity.this))
                .setNegativeButton("Cancelar", null)
                .show();
    }
}