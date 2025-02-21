package com.borges.exacuity.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.borges.exacuity.R;
import com.borges.exacuity.activities.ContrastActivity;
import com.borges.exacuity.activities.DaltonismActivity;
import com.borges.exacuity.activities.ExhibitionActivity;
import com.borges.exacuity.activities.ImageActivity;
import com.borges.exacuity.activities.SimulatorActivity;

public class ExhibitionUtils {
    public static void startDefaultActivity(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        int defaultMode = preferences.getInt("chart_index", 0);

        if (defaultMode == 0) return;

        startActivity(context, iconIds[defaultMode - 1]);  // As 'menu' is index 0

    }

    public static void startActivity(Context context, int mode) {
        if (mode == R.drawable.icon_g9) {
            Intent contrastIntent = new Intent(context, ContrastActivity.class);
            context.startActivity(contrastIntent);
        } else if (mode == R.drawable.icon_g10){
            Intent dotIntent = new Intent(context, ImageActivity.class);
            dotIntent.putExtra("image_source", R.drawable.dot);
            context.startActivity(dotIntent);
        } else if (mode == R.drawable.icon_g11){
            Intent daltonismIntent = new Intent(context, DaltonismActivity.class);
            context.startActivity(daltonismIntent);
        } else if (mode == R.drawable.icon_g12) {
            Intent amslerIntent = new Intent(context, ImageActivity.class);
            amslerIntent.putExtra("image_source", R.drawable.amsler);
            context.startActivity(amslerIntent);
        } else if (mode == R.drawable.icon_g13) {
            Intent templateIntent = new Intent(context, ImageActivity.class);
            templateIntent.putExtra("image_source", R.drawable.template);
            context.startActivity(templateIntent);
        } else if (mode == R.drawable.icon_g14) {
            Intent simulatorIntent = new Intent(context, SimulatorActivity.class);
            context.startActivity(simulatorIntent);
        } else if (mode == R.drawable.icon_g15) {
            Intent dialIntent = new Intent(context, ImageActivity.class);
            dialIntent.putExtra("image_source", R.drawable.dial);
            context.startActivity(dialIntent);
        } else {
            Intent intentExhibition = new Intent(context, ExhibitionActivity.class);
            intentExhibition.putExtra("iconResId", mode);
            context.startActivity(intentExhibition);
        }
    }

    public static final int[] iconIds = {
            R.drawable.icon_g1,
            R.drawable.icon_g2,
            R.drawable.icon_g3,
            R.drawable.icon_g4,
            R.drawable.icon_g5,
            R.drawable.icon_g6,
            R.drawable.icon_g7,
            R.drawable.icon_g8,
            R.drawable.icon_g9,
            R.drawable.icon_g10,
            R.drawable.icon_g11,
            R.drawable.icon_g12,
            R.drawable.icon_g13,
            R.drawable.icon_g14,
            R.drawable.icon_g15,

    };

    public static final String[] exhibitionAcuities = {
            "20/15",
            "20/20",
            "20/25",
            "20/30",
            "20/40",
            "20/50",
            "20/60",
            "20/80",
            "20/100",
            "20/125",
            "20/160",
            "20/200",
            "20/250",
            "20/320",
            "20/400"
    };

    public static final String[] exhibitionPercentages = {
            "103.6%",
            "100%",
            "95.6%",
            "89.8%",
            "83.6%",
            "76.5%",
            "67.5%",
            "58.5%",
            "48.9%",
            "38.8%",
            "28.6%",
            "20%",
            "16%",
            "10%",
            "3.3%"
    };

    public static char[] getCharSet(int mode) {
        char[] charSet;
        switch(mode) {
            case 0:
            default:
                charSet = new char[]{
                        'N', 'C', 'K', 'Z', 'O', 'R', 'H', 'V',
                        'D', 'P', 'F'
                };
                break;
            case 1:
                charSet = new char[]{
                        '1', '2', '3', '4', '5', '6', '7', '8',
                        '9'
                };
                break;
            case 2:
                charSet = new char[] {
                        'g', 'h', 'i', 'j', 'k', 'l', 'm'
                };
                break;
            case 3:
                charSet = new char[]{
                        'Q', 'W', 'X', 'Y'
                };
                break;
            case 4:
                charSet = new char[] {
                        'w', 'x', 'y', 'z'
                };
                break;
            case 5:
                charSet = new char[] {
                        'a', 'b', 'c', 'd'
                };
                break;
            case 6:
                charSet = new char[] {
                        'H', 'T', 'O', 'V'
                };
                break;
            case 7:
                charSet = new char[] {
                        'n', 'o', 'p', 'q'
                };
                break;
        }

        return charSet;
    }

    /**
     * Maps the icon resource ID to a mode value.
     * Adjust this logic based on your icon resources.
     */
    public static int convertIconIdToMode(int iconId) {
        // Example mapping: you can change these to match your resource IDs.
        if (iconId == R.drawable.icon_g1) {
            return 0;
        } else if (iconId == R.drawable.icon_g2) {
            return 1;
        } else if (iconId == R.drawable.icon_g3) {
            return 2;
        } else if (iconId == R.drawable.icon_g4) {
            return 3;
        } else if (iconId == R.drawable.icon_g5) {
            return 4;
        } else if (iconId == R.drawable.icon_g6) {
            return 5;
        } else if (iconId == R.drawable.icon_g7) {
            return 6;
        } else if (iconId == R.drawable.icon_g8) {
            return 7;
        } else if (iconId == R.drawable.icon_g9) {
            return 8;
        }
        return 0;
    }

    public static final int[] ishiwaraIcons = {
            R.drawable.ishiwara_01,
            R.drawable.ishiwara_02,
            R.drawable.ishiwara_03,
            R.drawable.ishiwara_04,
            R.drawable.ishiwara_05,
            R.drawable.ishiwara_06,
            R.drawable.ishiwara_07,
            R.drawable.ishiwara_08,
            R.drawable.ishiwara_09,
            R.drawable.ishiwara_10,
            R.drawable.ishiwara_11,
            R.drawable.ishiwara_12,
            R.drawable.ishiwara_13,
            R.drawable.ishiwara_14,
            R.drawable.ishiwara_15,
            R.drawable.ishiwara_16,
            R.drawable.ishiwara_17,
            R.drawable.ishiwara_18,
            R.drawable.ishiwara_19,
            R.drawable.ishiwara_20,
            R.drawable.ishiwara_21,
            R.drawable.ishiwara_22,
            R.drawable.ishiwara_23,
            R.drawable.ishiwara_24,
            R.drawable.ishiwara_25,
            R.drawable.ishiwara_26,
            R.drawable.ishiwara_27,
            R.drawable.ishiwara_28,
            R.drawable.ishiwara_29,
            R.drawable.ishiwara_30,
            R.drawable.ishiwara_31,
            R.drawable.ishiwara_32,
            R.drawable.ishiwara_33,
            R.drawable.ishiwara_34,
            R.drawable.ishiwara_35
    };

    public static final String[] ishiwaraDescriptions = {
        "Todo mundo vê 12",
        "Vê o número 8: normal. Vê o número 3: Deficiência verde-vermelha",
        "Vê o número 6: normal. Vê o número 5: Deficiência verde-vermelha",
        "Vê o número 29: normal. Vê o número 70: Deficiência verde-vermelha",
        "Vê o número 57: normal. Vê o número 35: Deficiência verde-vermelha",
        "Vê o número 5: normal. Vê o número 2: Deficiência verde-vermelha",
        "Vê o número 3: normal. Vê o número 5: Deficiência verde-vermelha",
        "Vê o número 15: normal. Vê o número 17: Deficiência verde-vermelha",
        "Vê o número 74: normal. Vê o número 21: Deficiência verde-vermelha",
        "Vê o número 2: normal. Não vê nada: Deficiência verde-vermelha",
        "Vê o número 6: normal. Não vê nada: Deficiência verde-vermelha",
        "Vê o número 97: normal. Não vê nada: Deficiência verde-vermelha",
        "Ver o número 45: normal. Não vê nada: Deficiência verde-vermelha",
        "Vê o número 5: normal. Não vê nada: Deficiência verde-vermelha",
        "Vê o número 7: normal. Não vê nada: Deficiência verde-vermelha",
        "Vê o número 16: normal. Não vê nada: Deficiência verde-vermelha",
        "Vê o número 73: normal. Não vê nada: Deficiência verde-vermelha",
        "Não vê nada: normal. Vê o número 5: Deficiência verde-vermelha",
        " Não vê nada: normal. Vê o número 2: Deficiência verde-vermelha",
        " Não vê nada: normal. Vê o número 45: Deficiência verde-vermelha",
        " Não vê nada: normal. Vê o número 73: Deficiência verde-vermelha",
        "Vê o número 26: normal. Vê o número 6: protanopia ou protanomalia. Vê o número 2: deuteranopia ou deuteranomalia",
        " Vê o número 42: normal. Vê o número 2:  protanopia ou protanomalia. Vê o número 4: deuteranopia ou deuteranomalia",
        " Vê o número 35: normal. Vê o número 5:  protanopia ou protanomalia. Vê o número 3: deuteranopia ou deuteranomalia",
        " Vê o número 96: normal. Vê o número 9:  protanopia ou protanomalia. Vê o número 6: deuteranopia ou deuteranomali",
        "Vê pontos roxos e vermelhos: NORMAL. Vê apenas a linha roxa: PROTANOPIA/PROTANOMALIA. Vê apenas a linha vermelha: DEUTERANOPIA/ DEUTERANOMALIA",
        " Vê pontos roxos e vermelhos: NORMAL. Vê apenas a linha roxa:  PROTANOPIA/PROTANOMALIA. Vê apenas a linha vermelha: DEUTERANOPIA/ DEUTERANOMALIA",
        "Não vê nada: NORMAL. Vê uma linha: DEFICIÊNCIA VERDE-VERMELHA",
        "Não vê nada: NORMAL. Vê uma linha: DEFICIÊNCIA VERDE-VERMELHA",
        "Vê uma linha verde: NORMAL. Não vê nada: DEFICIÊNCIA VERDE-VERMELHA",
        "Vê uma linha verde: NORMAL. Não vê nada: DEFICIÊNCIA VERDE-VERMELHA",
        "Vê uma linha laranja: NORMAL. Não vê nada: DEFICIÊNCIA VERDE-VERMELHA",
        "Vê uma linha laranja: NORMAL. Não vê nada: DEFICIÊNCIA VERDE-VERMELHA",
        "Vê uma linha azul-verde: NORMAL. Vê linha vermelho-violeta: DEFICIÊNCIA VERMELHO-VERDE",
        " Vê uma linha azul-verde: NORMAL. Vê linha vermelho-violeta: DEFICIÊNCIA VERMELHO-VERDE",
        " Vê linha laranja-rosa: NORMAL. Vê linha azul-violeta: DEFICIÊNCIA VERDE-VERMELHA"
    };

    public static final float[] logCSValues = {
            2.0f,
            1.75f,
            1.5f,
            1.25f,
            1.0f,
            0.75f,
            0.5f,
            0.25f
    };
    public static final float[] contrastValues = {
            0.01f,
            0.02f,
            0.03f,
            0.06f,
            0.1f,
            0.32f,
            0.56f
    };
}
