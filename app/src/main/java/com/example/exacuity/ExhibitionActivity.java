package com.example.exacuity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.exacuity.utils.ExhibitionUtils;
import com.example.exacuity.utils.TextUtils;

// TODO: exclude hardcoded strings to exhibition
// TODO: use only TextUtils methods and properties
// TODO: set percentage and acuity by exhibitionUtils
// TODO: load by SharedPreferences values
// TODO: load random text

public class ExhibitionActivity extends AppCompatActivity {

    private int localAcuityIndex; // Local copy of the initial index
    private TextView acuityText;
    private TextView percentageText;
    private TextView lettersText;
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
        lettersText = findViewById(R.id.text_letras);
        lettersText.setText(joinSymbols());

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

    private void updateAcuityDisplay() {
        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    /**
     * Joins all symbols into a single string separated by a space.
     */
    private String joinSymbols() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < symbols.length; i++) {
            sb.append(symbols[i]);
            if (i < symbols.length - 1) {
                sb.append("\u00A0");
            }
        }
        return sb.toString();
    }

    /**
     * Updates the letters display.
     * <p>
     * Behavior:
     * - If in the initial state (currentIndex == -1):
     *     · DPAD_RIGHT sets currentIndex to 0 (first symbol)
     *     · DPAD_LEFT sets currentIndex to symbols.length - 1 (last symbol)
     * - Otherwise:
     *     · For DPAD_LEFT, if currentIndex is 0 then reset to initial state (-1); else decrement.
     *     · For DPAD_RIGHT, if currentIndex is the last symbol then reset to initial state (-1); else increment.
     * <p>
     * After updating, if currentIndex is -1, the joined symbols string is shown. Otherwise, a SpannableString is built
     * that keeps all symbol positions but makes non-selected symbols transparent.
     *
     * @param direction -1 for left arrow, 1 for right arrow.
     */
    private void updateLetterText(int direction) {
        if (symbols.length == 0) return;

        if (currentIndex == -1) {
            // From initial state, choose starting symbol based on direction.
            if (direction == 1) {
                currentIndex = 0;
            } else if (direction == -1) {
                currentIndex = symbols.length - 1;
            }
        } else {
            if (direction == -1) {
                // Left arrow: reverse cycle.
                if (currentIndex == 0) {
                    currentIndex = -1; // Reset to initial state.
                } else {
                    currentIndex--;
                }
            } else if (direction == 1) {
                // Right arrow: forward cycle.
                if (currentIndex == symbols.length - 1) {
                    currentIndex = -1; // Reset to initial state.
                } else {
                    currentIndex++;
                }
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
                if (i < symbols.length - 1) {
                    sb.append(" ");
                }
            }

            SpannableString spannable = new SpannableString(sb.toString());
            // Hide all symbols except the one at currentIndex.
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
    private void increaseTextSize() {
        TextUtils.increaseTextSize(lettersText, 2);
    }

    private void decreaseTextSize() {
        TextUtils.decreaseTextSize(lettersText, 2, 12);
    }
}