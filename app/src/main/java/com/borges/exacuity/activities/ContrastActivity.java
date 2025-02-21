package com.borges.exacuity.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.borges.exacuity.R;
import com.borges.exacuity.utils.ExhibitionUtils;
import com.borges.exacuity.utils.OptotypeUtils;

public class ContrastActivity extends AppCompatActivity {
    private TextView lettersText;
    private TextView logCSText;
    private TextView contrastText;
    private OptotypeUtils optotypeUtils;
    private int contrastIndex = 6; // starting index
    private final int maxIndex = ExhibitionUtils.contrastValues.length - 1;

    private float distance;
    private int calibrator; // Default calibrator (mm) is 80

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast);

        SharedPreferences prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        int localAcuityIndex = prefs.getInt("acuity_index", 0);
        distance = prefs.getFloat("distance", 4.0f);
        calibrator = prefs.getInt("e_height", 80);

        logCSText = findViewById(R.id.text_logcs);
        contrastText = findViewById(R.id.text_percentage);
        lettersText = findViewById(R.id.text_letras);

        optotypeUtils = new OptotypeUtils(null, logCSText, lettersText, localAcuityIndex);

        lettersText.post(() -> {
            lettersText.setText(optotypeUtils.generateOptotypes(0));
            lettersText.setTextSize((float) (0.0725 * distance * 60 * 3.779 * 50 / calibrator));
            updateContrastDisplay();

            new Handler().postDelayed(
                    () -> lettersText.setText(optotypeUtils.generateOptotypes(0)), 0
            );
        });
    }

    @SuppressLint("DefaultLocale")
    private void updateContrastDisplay() {
        float contrast = ExhibitionUtils.contrastValues[contrastIndex];
        int alpha = (int) (contrast * 255);
        if (alpha > 255) {
            alpha = 255;
        }
        int color = (alpha << 24);
        lettersText.setTextColor(color);

        float logCS = ExhibitionUtils.logCSValues[contrastIndex];
        logCSText.setText(String.format("logCS: %.2f", logCS));

        int percent = Math.round(contrast * 100);
        contrastText.setText(String.format("Contraste: %d%%", percent));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (contrastIndex < maxIndex) {
                    contrastIndex++;
                    updateContrastDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (contrastIndex > 0) {
                    contrastIndex--;
                    updateContrastDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                optotypeUtils.updateLetterText(-1);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                optotypeUtils.updateLetterText(1);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}