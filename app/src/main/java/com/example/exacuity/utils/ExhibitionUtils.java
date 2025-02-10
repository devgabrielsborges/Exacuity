package com.example.exacuity.utils;

import com.example.exacuity.R;

public class ExhibitionUtils {
    public static String[] exhibitionAcuities = {
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

    public static String[] exhibitionPercentages = {
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
            case 8:
            default:
                charSet = new char[]{
                        'A', 'B', 'C', 'D', 'E', 'F', 'H', 'K',
                        'L', 'N', 'O', 'P'
                };
                break;
            case 1:
                charSet = new char[]{
                        '1', '2', '3', '4', '5', '6', '7', '8',
                        '9'
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
                        'H', 'K', 'L', 'N', 'O', 'P'
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
}
