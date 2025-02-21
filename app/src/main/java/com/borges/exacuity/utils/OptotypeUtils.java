package com.borges.exacuity.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.TextView;

import com.borges.exacuity.activities.ExhibitionActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OptotypeUtils {
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

    public float sizeOptotype(float distance, int acuity_index, float calibratorMm, ExhibitionActivity context) {
        String acuityStr = ExhibitionUtils.exhibitionAcuities[acuity_index];
        String[] parts = acuityStr.split("/");
        int acuityDenom = Integer.parseInt(parts[1]);

        double letterAngleRad = (acuityDenom / 20.0) * Math.toRadians(5.0 / 60.0);

        double sizeMeters = 2 * distance * Math.tan(letterAngleRad / 2);

        double sizeMm = sizeMeters * 1000;

        double adjustedSizeMm = sizeMm * (calibratorMm / 80.0);

        float sizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                (float) adjustedSizeMm,
                context.getResources().getDisplayMetrics()
        );

        if (sizePx < 1.0f) {
            sizePx = 1.0f;
        }

        return sizePx * 1.1f;
    }

    public String generateOptotypes(int mode) {
        char[] charSet = ExhibitionUtils.getCharSet(mode);

        int availableWidth = lettersText.getWidth();
        if (availableWidth <= 0) {
            float typicalLetterWidth = lettersText.getPaint().measureText("A");
            availableWidth = (int) (5 * typicalLetterWidth);
        }
        String typicalLetter = "E";
        float letterWidth = lettersText.getPaint().measureText(typicalLetter);

        float additionalSpacing = lettersText.getLetterSpacing() * lettersText.getTextSize();
        float fullLetterWidth = letterWidth + additionalSpacing;

        int maxLetters = (int) (availableWidth / fullLetterWidth);
        maxLetters = Math.max(maxLetters, 1);

        int length = Math.min(maxLetters, 5);

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        symbols = new String[length];
        Map<Character, Integer> frequencyMap = new HashMap<>();

        int count = 0;
        while (count < length) {
            int randomIndex = random.nextInt(charSet.length);
            char letter = charSet[randomIndex];

            int frequency = frequencyMap.getOrDefault(letter, 0);
            switch (mode) {
                case 0:
                case 1:
                case 2:
                    if (frequency == 0) {
                        sb.append(letter);
                        symbols[count] = String.valueOf(letter);
                        frequencyMap.put(letter, frequency + 1);
                        count++;
                    }
                    break;
                default:
                    if (frequency < 2) {  // Max 2 repetitions
                        sb.append(letter);
                        symbols[count] = String.valueOf(letter);
                        frequencyMap.put(letter, frequency + 1);
                        count++;
                    }

            }
        }

        currentIndex = -1;
        return sb.toString();
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
            currentIndex = (direction == 1) ? 0 : symbols.length - 1;
        } else {
            if (direction == -1) {
                currentIndex = (currentIndex == 0) ? -1 : currentIndex - 1;
            } else if (direction == 1) {
                currentIndex = (currentIndex == symbols.length - 1) ? -1 : currentIndex + 1;
            }
        }

        if (currentIndex == -1) {
            lettersText.setText(joinSymbols());
        } else {
            StringBuilder sb = new StringBuilder();
            int[] startIndices = new int[symbols.length];
            int[] endIndices = new int[symbols.length];
            for (int i = 0; i < symbols.length; i++) {
                startIndices[i] = sb.length();
                sb.append(symbols[i]);
                endIndices[i] = sb.length();
            }
            SpannableString spannable = new SpannableString(sb.toString());
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