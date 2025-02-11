package com.example.exacuity.utils;

public class OptotypeUtils {

    public static float sizeOptotype(float distance, int acuity_index, int calibrator) {
        String acuityText = ExhibitionUtils.exhibitionAcuities[acuity_index];
        String[] acuityParts = acuityText.split("/");

        int acuity = Integer.parseInt(acuityParts[1]);  // Taking the denominator part

        return (float) (0.0725*distance*acuity *3.779*50/calibrator);
    }
}