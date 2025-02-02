package com.example.exacuity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.exacuity.MainActivity;

public class SettingsUtils {

    public static void initializeDefaultSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

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

    public static void setSetting(Context context, String field, String value) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        switch (field) {
            case "distance":
            case "screen_size":
                editor.putFloat(field, Float.parseFloat(value));
                break;
            case "initial_acuity":
            case "initial_table":
                editor.putString(field, value);
                break;
            case "name_field":
                String formattedName = adjustNameField(context, value);
                editor.putString(field, formattedName);
                break;
            default:
                break;
        }

        editor.apply();
    }

    private static boolean isNameFieldOk(String nameField) {
        return nameField != null && !nameField.isEmpty();
    }

    public static String adjustNameField(Context context, String nameField) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (!isNameFieldOk(nameField)) {
            return preferences.getString("name_field", "");  // Return default if invalid
        }

        String[] words = nameField.trim().toLowerCase().split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        String finalName = formattedName.toString().trim();
        editor.putString("name_field", finalName);
        editor.apply();

        return finalName;
    }
}

