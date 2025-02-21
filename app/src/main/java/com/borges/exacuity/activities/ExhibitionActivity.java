package com.borges.exacuity.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.borges.exacuity.R;
import com.borges.exacuity.utils.ExhibitionUtils;
import com.borges.exacuity.utils.OptotypeUtils;

public class ExhibitionActivity extends AppCompatActivity {

    private int localAcuityIndex;
    private float distance;
    private int calibrator; // Default calibrator (in mm) is 80
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

        int iconResId = getIntent().getIntExtra("iconResId", 0);
        mode = ExhibitionUtils.convertIconIdToMode(iconResId);

        optotypeUtils = new OptotypeUtils(acuityText, percentageText, lettersText, localAcuityIndex);

        lettersText.post(() -> {
            updateOptotypeDisplay();

            new Handler().postDelayed(() -> {
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP));

                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN));
            }, 0);
        });
    }

    private void updateOptotypeDisplay(){
        String optotypes = optotypeUtils.generateOptotypes(mode);
        lettersText.setText(optotypes);

        float calculatedSize = optotypeUtils.sizeOptotype(distance, localAcuityIndex, (float) calibrator, this);
        Log.d("ExhibitionActivity", "Calculated optotype size in pixels: " + calculatedSize);
        lettersText.setTextSize(TypedValue.COMPLEX_UNIT_PX, calculatedSize);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                lettersText.setText(optotypeUtils.generateOptotypes(mode));
                if (localAcuityIndex < ExhibitionUtils.exhibitionAcuities.length - 1) {
                    localAcuityIndex++;
                    float updatedSizeUp = optotypeUtils.sizeOptotype(distance, localAcuityIndex, (float) calibrator, this);
                    Log.d("ExhibitionActivity", "Updated optotype size on UP: " + updatedSizeUp);
                    lettersText.setTextSize(TypedValue.COMPLEX_UNIT_PX, updatedSizeUp);
                    optotypeUtils.setLocalAcuityIndex(localAcuityIndex);
                    optotypeUtils.updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                lettersText.setText(optotypeUtils.generateOptotypes(mode));
                if (localAcuityIndex > 0) {
                    localAcuityIndex--;
                    float updatedSizeDown = optotypeUtils.sizeOptotype(distance, localAcuityIndex, (float) calibrator, this);
                    Log.d("ExhibitionActivity", "Updated optotype size on DOWN: " + updatedSizeDown);
                    lettersText.setTextSize(TypedValue.COMPLEX_UNIT_PX, updatedSizeDown);
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