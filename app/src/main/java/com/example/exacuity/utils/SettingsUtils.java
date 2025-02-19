package com.example.exacuity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.exacuity.activities.MainActivity;

public class SettingsUtils {

    public static final String[] charts = {
            "Menu", "Letras", "Numeros", "Tumble E", "Figuras",
            "C de Landolt", "Mao", "g7", "g8", "g9", "g10", "g11",
            "g12", "g13", "g14", "g15"
    };

    public static void initializeSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Check if defaults have already been set
        if (!preferences.contains("acuity")) {
            editor.putString("name_field", "Exacuity");

            editor.putInt("acuity_index", 0);
            editor.putInt("chart_index", 0);
            editor.putFloat("distance", 4.0f);
            editor.putInt("e_height", 87);

            editor.putString("config_bottom_text_0", preferences.getFloat("distance", 4.0f) + " m");
            editor.putString("config_bottom_text_1", preferences.getInt("e_height", 87) + " mm");
            editor.putString("config_bottom_text_2", ExhibitionUtils.exhibitionAcuities[preferences.getInt("acuity_index", 0)]);
            editor.putString("config_bottom_text_3", charts[preferences.getInt("chart_index", 0)]);

            editor.putString("acuity", preferences.getString("config_bottom_text_2", "20/15"));
            editor.putString("percentage", ExhibitionUtils.exhibitionPercentages[preferences.getInt("acuity_index", 0)]);

            editor.apply();
        }
    }

    public static void performSettingsReset(Activity activity) {
        activity.getSharedPreferences("AppSettings", Context.MODE_PRIVATE).edit().clear().apply();

        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            // Handle the scenario where the launch intent is not found
            Intent restartIntent = new Intent(activity, MainActivity.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(restartIntent);
        }

        activity.finish();
    }
}

