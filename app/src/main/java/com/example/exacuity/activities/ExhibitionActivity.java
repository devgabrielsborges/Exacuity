package com.example.exacuity.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exacuity.R;
import com.example.exacuity.utils.ExhibitionUtils;
import com.example.exacuity.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// TODO: use only TextUtils methods and properties

public class ExhibitionActivity extends AppCompatActivity {

    private int localAcuityIndex; // Local copy of the initial index
    private TextView acuityText;
    private TextView percentageText;
    private TextView lettersText;
    private String[] symbols;
    private int currentIndex = 0;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibition);

        SharedPreferences prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        localAcuityIndex = prefs.getInt("acuity_index", 0); // Read once from SharedPreferences

        acuityText = findViewById(R.id.text_acuidade);
        percentageText = findViewById(R.id.text_percentual);
        lettersText = findViewById(R.id.text_letras);

        // Extract the icon resource id from the intent extras.
        int iconResId = getIntent().getIntExtra("iconResId", 0);
        mode = ExhibitionUtils.convertIconIdToMode(iconResId);
        // Generate random 5-char string based on the mode's charSet.
        lettersText.setText(generateRandomLetterText(mode));

        // Use localAcuityIndex to initialize views.
        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    private String generateRandomLetterText(int mode) {
        char[] charSet = ExhibitionUtils.getCharSet(mode);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        symbols = new String[5];
        Map<Character, Integer> frequencyMap = new HashMap<>();

        int count = 0;
        while (count < 5) {
            int randomIndex = random.nextInt(charSet.length);
            char letter = charSet[randomIndex];

            // Ensure the map does not return null
            int frequency = frequencyMap.getOrDefault(letter, 0);

            if (frequency < 2) { // Allow at most 2 repetitions
                sb.append(letter);
                symbols[count] = String.valueOf(letter);
                frequencyMap.put(letter, frequency + 1);
                count++;
            }
        }

        return sb.toString();
    }

    private void updateAcuityDisplay() {
        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    /**
     * Joins all symbols into a single string separated by a non-breaking space.
     */
    private String joinSymbols() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < symbols.length; i++) {
            sb.append(symbols[i]);
            if (i < symbols.length - 1) {
                sb.append("");
            }
        }
        return sb.toString();
    }

    /**
     * Updates the letters display based on arrow key events.
     *
     * @param direction -1 for left arrow, 1 for right arrow.
     */
    private void updateLetterText(int direction) {
        if (symbols.length == 0)
            return;

        if (currentIndex == -1) {
            // From initial state, choose starting symbol based on direction.
            currentIndex = (direction == 1) ? 0 : symbols.length - 1;
        } else {
            if (direction == -1) {
                // Left arrow: reverse cycle.
                currentIndex = (currentIndex == 0) ? -1 : currentIndex - 1;
            } else if (direction == 1) {
                // Right arrow: forward cycle.
                currentIndex = (currentIndex == symbols.length - 1) ? -1 : currentIndex + 1;
            }
        }

        if (currentIndex == -1) {
            // Reset state: show all symbols.
            lettersText.setText(joinSymbols());
        } else {
            // Build a SpannableString preserving symbol positions.
            StringBuilder sb = new StringBuilder();
            int[] startIndices = new int[symbols.length];
            int[] endIndices = new int[symbols.length];
            for (int i = 0; i < symbols.length; i++) {
                startIndices[i] = sb.length();
                sb.append(symbols[i]);
                endIndices[i] = sb.length();
            }

            SpannableString spannable = new SpannableString(sb.toString());
            // Hide all symbols except the selected one.
            for (int i = 0; i < symbols.length; i++) {
                if (i != currentIndex) {
                    spannable.setSpan(
                            new ForegroundColorSpan(Color.TRANSPARENT),
                            startIndices[i],
                            endIndices[i],
                            0
                    );
                }
            }
            lettersText.setText(spannable);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (localAcuityIndex < ExhibitionUtils.exhibitionAcuities.length - 1) {
                    increaseTextSize();
                    lettersText.setText(generateRandomLetterText(mode));
                    localAcuityIndex++;
                    updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (localAcuityIndex > 0) {
                    decreaseTextSize();
                    lettersText.setText(generateRandomLetterText(mode));
                    localAcuityIndex--;
                    updateAcuityDisplay();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                updateLetterText(-1);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                updateLetterText(1);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                currentIndex = -1;
                lettersText.setText(joinSymbols());
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void increaseTextSize() {
        TextUtils.increaseTextSize(lettersText, 2);
    }

    private void decreaseTextSize() {
        TextUtils.decreaseTextSize(lettersText, 2, 12);
    }
}