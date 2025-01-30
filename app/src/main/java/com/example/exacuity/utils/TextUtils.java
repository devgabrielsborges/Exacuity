package com.example.exacuity.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

// TODO: set different characters set for exhibition
// TODO: determine increase values for text
// TODO: generate random text for each exhibition instance
// TODO: update other texts in exhibition, like scale

public class TextUtils {

    /**
     * Increases the text size of the given TextView by the specified SP value.
     *
     * @param textView The TextView whose text size will be increased.
     * @param sp       The value in SP to increase the text size by.
     */
    public static void increaseTextSize(TextView textView, float sp) {
        float currentSize = textView.getTextSize();
        float newSize = currentSize + spToPx(textView.getContext(), sp);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);
    }

    /**
     * Decreases the text size of the given TextView by the specified SP value.
     * Ensures that the text size does not go below a minimum size.
     *
     * @param textView The TextView whose text size will be decreased.
     * @param sp       The value in SP to decrease the text size by.
     * @param minSp    The minimum text size in SP.
     */
    public static void decreaseTextSize(TextView textView, float sp, float minSp) {
        float currentSize = textView.getTextSize();
        float newSize = currentSize - spToPx(textView.getContext(), sp);
        float minSizePx = spToPx(textView.getContext(), minSp);
        if (newSize >= minSizePx) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);
        }
    }

    /**
     * Converts SP units to Pixels.
     *
     * @param context The context to access resources and device-specific display metrics.
     * @param sp      The value in SP to convert.
     * @return The converted value in pixels.
     */
    private static float spToPx(Context context, float sp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                context.getResources().getDisplayMetrics()
        );
    }
}