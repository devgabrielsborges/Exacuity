package com.example.exacuity.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.example.exacuity.utils.SettingsUtils;

public class OptotypeUtils {
    public static float sizeOptotype () {
        float dist = SettingsUtils.editor.getFloat("distance", "");
        int acuid = SettingsUtils.editor.getInt("initial_acuity", "");
        float calibrator = SettingsUtils.editor.getInt("screen_size", "");

        return 0.075*dist*acuid*3.779*50/calibrator;  // optotype size
    }
}
