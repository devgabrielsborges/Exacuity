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

        GridAdapter adapter = new GridAdapter(this, iconIds);
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
            .setTitle("Resetar aplicativo")
            .setMessage("Deseja resetar o aplicativo?")
            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SettingsUtils.performSettingsReset(MainActivity.this);
                }
             })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}