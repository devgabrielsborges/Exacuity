package com.borges.exacuity.activities;

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

import com.borges.exacuity.R;
import com.borges.exacuity.adapters.GridAdapter;
import com.borges.exacuity.utils.ExhibitionUtils;
import com.borges.exacuity.utils.SettingsUtils;

// TODO: define sizes of optotypes

public class MainActivity extends AppCompatActivity { // Changed superclass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SettingsUtils.initializeSettings(this);
        setTitleName();

        ExhibitionUtils.startDefaultActivity(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5)); // 5 columns

        GridAdapter adapter = getGridAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.post(() -> {
            if (recyclerView.getChildCount() > 0) {
                recyclerView.getChildAt(0).requestFocus();
            }
        });

        Button buttonSettings = findViewById(R.id.buttonSettings);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonSettings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConfigurationsActivity.class);
            startActivity(intent);
        });

        buttonReset.setOnClickListener(view -> showResetConfirmationDialog());
    }

    private @NonNull GridAdapter getGridAdapter() {
        return new GridAdapter(this, ExhibitionUtils.iconIds);
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