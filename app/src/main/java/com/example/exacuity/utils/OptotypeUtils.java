package com.example.exacuity.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OptotypeUtils {
    // Instance fields (state)
    private int localAcuityIndex;
    private final TextView acuityText;
    private final TextView percentageText;
    private final TextView lettersText;
    private String[] symbols = new String[0];
    private int currentIndex = -1;

    public OptotypeUtils(TextView acuityText, TextView percentageText, TextView lettersText, int localAcuityIndex) {
        this.acuityText = acuityText;
        this.percentageText = percentageText;
        this.lettersText = lettersText;
        this.localAcuityIndex = localAcuityIndex;
    }

    public void setLocalAcuityIndex(int localAcuityIndex) {
        this.localAcuityIndex = localAcuityIndex;
    }

    public float sizeOptotype(float distance, int acuity_index, int calibrator) {
        String acuityStr = ExhibitionUtils.exhibitionAcuities[acuity_index];
        String[] parts = acuityStr.split("/");
        int acuity = Integer.parseInt(parts[1]); // using denominator
        return (float) (0.0725 * distance * acuity * 3.779 * 50 / calibrator);
    }

    public String generateOptotypes(int mode) {
        char[] charSet = ExhibitionUtils.getCharSet(mode);
        float lines = countLines(lettersText);
        int length = getOptotypeQuantity(lines);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        symbols = new String[length];
        Map<Character, Integer> frequencyMap = new HashMap<>();

        int count = 0;
        while (count < length) {
            int randomIndex = random.nextInt(charSet.length);
            char letter = charSet[randomIndex];

            int frequency = frequencyMap.getOrDefault(letter, 0);
            if (frequency < 2) { // allow at most two repetitions
                sb.append(letter);
                symbols[count] = String.valueOf(letter);
                frequencyMap.put(letter, frequency + 1);
                count++;
            }
        }

        // Reset currentIndex when generating a new optotype string.
        currentIndex = -1;
        return sb.toString();
    }

    private int getOptotypeQuantity(float lines) {
        if (lines > 0.5f && lines <= 1.5f) return 5;
        else if (lines <= 2.5f) return 4;
        else if (lines <= 3.5f) return 2;
        return 1;
    }

    private float countLines(@NonNull TextView textView) {
        if (textView.getLayout() != null) return textView.getLineCount();
        return (float) textView.getHeight() / textView.getLineHeight();
    }

    public void updateAcuityDisplay() {
        acuityText.setText(ExhibitionUtils.exhibitionAcuities[localAcuityIndex]);
        percentageText.setText(ExhibitionUtils.exhibitionPercentages[localAcuityIndex]);
    }

    public String joinSymbols() {
        StringBuilder sb = new StringBuilder();
        for (String symbol : symbols) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    public void updateLetterText(int direction) {
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
}