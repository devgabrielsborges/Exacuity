package com.example.exacuity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OptotypeUtils {

    public static float sizeOptotype(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);

        float dist = preferences.getFloat("distance", 4.0f);  // Default value: 4.0f
        String acuity = preferences.getString("initial_acuity", "20/20");  // Default value: "20/20"
        float calibrator = preferences.getFloat("screen_size", 87.0f);  // Default value: 87.0f

        String[] acuityParts = acuity.split("/");
        int acuid = Integer.parseInt(acuityParts[0]);  // Taking the numerator part

        // Calculate the optotype size
        return 0.075f * dist * acuid * 3.779f * 50 / calibrator;
    }
}