package com.example.exacuity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity; // Changed import
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exacuity.utils.SettingsUtils;

// TODO: create e-size exhibition
// TODO: fix whole application style

public class MainActivity extends AppCompatActivity { // Changed superclass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SettingsUtils.initializeDefaultSettings(this);

         RecyclerView recyclerView = findViewById(R.id.recyclerView);
         recyclerView.setLayoutManager(new GridLayoutManager(this, 5)); // 5 columns

         String[] symbols = {"E", "H", "Ш", "E", "M", "E", "H", "Ш", "E", "M", "E", "Ш", "E", "M", "G"};
         GridAdapter adapter = new GridAdapter(this, symbols);
         recyclerView.setAdapter(adapter);

        Button buttonSettings = findViewById(R.id.buttonSettings);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigurationsActivity.class);
                startActivity(intent);
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResetConfirmationDialog();
            }
        });
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(this) // No changes needed here
            .setTitle("Confirm Reset")
            .setMessage("Are you sure you want to reset the app?")
            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SettingsUtils.performSettingsReset(MainActivity.this);
                }
             })
            .setNegativeButton("Cancel", null)
            .show();
    }
}