package com.example.exacuity.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exacuity.R;
import com.example.exacuity.utils.ExhibitionUtils;
import com.example.exacuity.utils.OptotypeUtils;

public class ExhibitionActivity extends AppCompatActivity {

    private int localAcuityIndex; // local acuity index from preferences
    private float distance;
    private int calibrator;
    private TextView lettersText;
    private int mode;
    private OptotypeUtils optotypeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibition);

        SharedPreferences prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        localAcuityIndex = prefs.getInt("acuity_index", 0);
        distance = prefs.getFloat("distance", 4.0f);
        calibrator = prefs.getInt("e_height", 80);

        TextView acuityText = findViewById(R.id.text_acuidade);
        TextView percentageText = findViewById(R.id.text_percentual);
        lettersText = findViewById(R.id.text_letras);

        // Extract the icon resource id from the intent extras.
        int iconResId = getIntent().getIntExtra("iconResId", 0);
        mode = ExhibitionUtils.convertIconIdToMode(iconResId);

        // Create an instance of OptotypeUtils with necessary views and state.
        optotypeUtils = new OptotypeUtils(acuityText, percentageText, lettersText, localAcuityIndex);

        // Generate optotypes text based on mode and update display.
        lettersText.setText(optotypeUtils.generateOptotypes(mode));
        lettersText.setTextSize(optotypeUtils.sizeOptotype(distance, localAcuityIndex, calibrator));

        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                lettersText.setText(optotypeUtils.generateOptotypes(mode));
                if (localAcuityIndex < ExhibitionUtils.exhibitionAcuities.length - 1) {
                    localAcuityIndex++;
                    // Update both the text size and the internal acuity index.
                    lettersText.setTextSize(optotypeUtils.sizeOptotype(distance, localAcuityIndex, calibrator));
                    optotypeUtils.setLocalAcuityIndex(localAcuityIndex);
                    optotypeUtils.updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                lettersText.setText(optotypeUtils.generateOptotypes(mode));
                if (localAcuityIndex > 0) {
                    localAcuityIndex--;
                    lettersText.setTextSize(optotypeUtils.sizeOptotype(distance, localAcuityIndex, calibrator));
                    optotypeUtils.setLocalAcuityIndex(localAcuityIndex);
                    optotypeUtils.updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                optotypeUtils.updateLetterText(-1);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                optotypeUtils.updateLetterText(1);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                lettersText.setText(optotypeUtils.joinSymbols());
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}