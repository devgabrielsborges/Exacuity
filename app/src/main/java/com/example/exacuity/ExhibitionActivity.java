package com.example.exacuity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exacuity.utils.TextUtils;

// TODO: exclude hardcoded strings to exhibition
// TODO: use only TextUtils methods and properties

public class ExhibitionActivity extends AppCompatActivity {
    private TextView letrasText;
    private String[] symbols = {"A", "B", "C", "D", "E"};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibition);

        TextView acuityText = findViewById(R.id.text_acuidade);
        letrasText = findViewById(R.id.text_letras);
        TextView odText = findViewById(R.id.text_od);
        TextView percentageText = findViewById(R.id.text_percentual);
        TextView oeText = findViewById(R.id.text_oe);

        String symbol = getIntent().getStringExtra("symbol");
        if (symbol != null) {
            letrasText.setText(symbol);
        }

        SharedPreferences prefs = this.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        String acuity = prefs.getString("acuity", "20/20");
        String percentage = prefs.getString("percentage", "100.0%");

        acuityText.setText(acuity);
        percentageText.setText(percentage);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                updateLetrasText(-1);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                updateLetrasText(1);
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                increaseTextSize();
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                decreaseTextSize();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void updateLetrasText(int direction) {
        if (symbols.length == 0) return;

        currentIndex += direction;

        if (currentIndex < 0) {
            currentIndex = symbols.length - 1;
        } else if (currentIndex >= symbols.length) {
            currentIndex = 0;
        }

        letrasText.setText(symbols[currentIndex]);
    }

    private void increaseTextSize() {
        // Increase text size by 2sp using TextUtils
        TextUtils.increaseTextSize(letrasText, 2);
    }

    private void decreaseTextSize() {
        // Decrease text size by 2sp using TextUtils, minimum size 12sp
        TextUtils.decreaseTextSize(letrasText, 2, 12);
    }
}