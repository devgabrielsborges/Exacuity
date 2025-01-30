package com.example.exacuity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity; // Changed import
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// TODO: create e-size exhibition
// TODO: fix whole application style

public class MainActivity extends AppCompatActivity { // Changed superclass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDefaultSettings();

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

    private void initializeDefaultSettings () {
        SharedPreferences preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Check if defaults have already been set
        if (!preferences.contains("distance")) {
            editor.putFloat("distance", 4.0f);
            editor.putString("acuity", "20/20");
            editor.putString("screen_size", "87mm");
            editor.putString("initial_table", "20/80");

            editor.apply();
        }
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(this) // No changes needed here
            .setTitle("Confirm Reset")
            .setMessage("Are you sure you want to reset the app?")
            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    performAppReset();
                }
             })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void performAppReset() {
        // Example: Clearing SharedPreferences
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit().clear().apply();
    
        // Example: Restarting the app
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            // Handle the scenario where the launch intent is not found
            // For example, you can start MainActivity directly
            Intent restartIntent = new Intent(this, MainActivity.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(restartIntent);
        }
        
        finish();
    }
}