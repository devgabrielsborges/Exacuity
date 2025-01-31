package com.example.exacuity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.exacuity.MainActivity;

public class SettingsUtils {
    public static SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
    public static SharedPreferences.Editor editor = preferences.edit();

    public static void initializeDefaultSettings (Context context) {
        // Check if defaults have already been set
        if (!preferences.contains("distance")) {
            editor.putFloat("distance", 4.0f);
            editor.putInt("screen_size", 87);
            editor.putString("initial_acuity", "20/20");
            editor.putString("initial_table", "20/80");
            editor.putString("name_field", "Exacuity");

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

    public static void setSetting(String[] field, String[] value) {
        switch (field) {
            case "distance":
            case "screen_size":
                editor.putFloat(field, value);
                break;

            case "initial_acuity":
            case "initial_table":
                editor.putString(field, value);
                
                break;
            case "name_field":
                adjustNameField(value);
                editor.putString(field, value);
                break;
        
            default:
                break;
        }

    }

    private static boolean isNameFieldOk(String nameField) {
        if (nameField == "" || nameField == null || nameField.trim().isEmpty()) {
            return false;
        }
    }

    public static String[] adjustNameField(String nameField) {
        if (!isNameFieldOk(nameField)) return editor.getString("nameField", "");

        String[] words = nameField.trim().toLowerCase().split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                             .append(word.substring(1))
                             .append(" ");
            }
        }

        return formattedName.toString().trim();
    }
}
