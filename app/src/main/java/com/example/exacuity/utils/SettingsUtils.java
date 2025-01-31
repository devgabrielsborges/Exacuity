package com.example.exacuity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.exacuity.MainActivity;

public class SettingsUtils {
    public static void initializeDefaultSettings (Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
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

    public static void performSettingsReset(Activity activity) {
        // Example: Clearing SharedPreferences
        activity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit().clear().apply();
    
        // Example: Restarting the app
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            // Handle the scenario where the launch intent is not found
            // For example, you can start MainActivity directly
            Intent restartIntent = new Intent(activity, MainActivity.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(restartIntent);
        }
        
        activity.finish();
    }
}
