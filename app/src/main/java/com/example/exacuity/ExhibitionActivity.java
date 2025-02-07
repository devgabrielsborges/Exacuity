package com.example.exacuity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.exacuity.utils.ExhibitionUtils;
import com.example.exacuity.utils.TextUtils;

// TODO: exclude hardcoded strings to exhibition
// TODO: use only TextUtils methods and properties
// TODO: set percentage and acuitys by exhibitionUtils
// TODO: load by SharedPreferences values
// TODO: load random text

public class ExhibitionActivity extends AppCompatActivity {

    private int localAcuityIndex; // Local copy of the initial index
    private TextView acuityText;
    private TextView percentageText;
    private TextView letrasText;
    private final String[] symbols = {"A", "B", "C", "D", "E"};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibition);

        SharedPreferences prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        localAcuityIndex = prefs.getInt("acuity_index", 0); // Read once from SharedPreferences

        acuityText = findViewById(R.id.text_acuidade);
        percentageText = findViewById(R.id.text_percentual);
        letrasText = findViewById(R.id.text_letras);

        // Use localAcuityIndex to initialize views.
        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (localAcuityIndex < ExhibitionUtils.exhibitionAcuities.length - 1) {
                    increaseTextSize();
                    localAcuityIndex++;
                    updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (localAcuityIndex > 0) {
                    decreaseTextSize();
                    localAcuityIndex--;
                    updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                updateLetrasText(-1);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                updateLetrasText(1);
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void updateAcuityDisplay() {
        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    private void updateLetrasText(int direction) {
        if (symbols.length == 0) return;
        currentIndex = (currentIndex + direction + symbols.length) % symbols.length;
        letrasText.setText(symbols[currentIndex]);
    }

    private void increaseTextSize() {
        TextUtils.increaseTextSize(letrasText, 2);
    }

    private void decreaseTextSize() {
        TextUtils.decreaseTextSize(letrasText, 2, 12);
    }
}