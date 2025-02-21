package com.borges.exacuity.activities;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.borges.exacuity.R;

public class SimulatorActivity extends AppCompatActivity {
    private TextView cataractText;
    private TextView glaucomaText;

    private ImageButton leftArrow;
    private ImageButton rightArrow;

    private View fogOverlay;
    private View glaucomaOverlay;

    private int defaultColor;
    private int alternateColor;

    // Toggle flags for text colors
    private boolean isCatarataAlternate = false;
    private boolean isGlaucomaAlternate = false;

    private final long ANIM_DURATION = 1200; // fade in/out duration in ms.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        cataractText = findViewById(R.id.item_catarata);
        glaucomaText = findViewById(R.id.item_glaucoma);

        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);

        RelativeLayout rootLayout = findViewById(R.id.simulator_root);

        Resources res = getResources();
        defaultColor = res.getColor(R.color.title_color);
        alternateColor = res.getColor(R.color.button_pressed_color);

        GradientDrawable fogDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{ Color.argb(0, 255, 255, 255), Color.argb(220, 220, 220, 220) }
        );
        fogOverlay = new View(this);
        RelativeLayout.LayoutParams fogParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        fogOverlay.setLayoutParams(fogParams);
        fogOverlay.setBackground(fogDrawable);
        fogOverlay.setAlpha(0.85f);
        fogOverlay.setVisibility(View.GONE);
        rootLayout.addView(fogOverlay);

        GradientDrawable glaucomaDrawable = getGradientDrawable();
        glaucomaOverlay = new View(this);
        RelativeLayout.LayoutParams glaucParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        glaucomaOverlay.setLayoutParams(glaucParams);
        glaucomaOverlay.setBackground(glaucomaDrawable);
        glaucomaOverlay.setAlpha(0f);
        glaucomaOverlay.setVisibility(View.GONE);
        rootLayout.addView(glaucomaOverlay);
    }

    private static @NonNull GradientDrawable getGradientDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        drawable.setGradientCenter(0.5f, 0.5f);
        drawable.setGradientRadius(500f); // Adjust radius as needed.
        int[] colors = new int[]{ Color.TRANSPARENT, Color.BLACK, Color.BLACK };
        float[] positions = new float[]{ 0f, 0.5f, 1f };
        drawable.setColors(colors, positions);
        return drawable;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            isCatarataAlternate = !isCatarataAlternate;
            cataractText.setTextColor(isCatarataAlternate ? alternateColor : defaultColor);
            rightArrow.setColorFilter(alternateColor);

            if (fogOverlay.getVisibility() == View.VISIBLE) {
                fadeOutOverlay(fogOverlay);
            } else {
                fadeInOverlay(fogOverlay);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            isGlaucomaAlternate = !isGlaucomaAlternate;
            glaucomaText.setTextColor(isGlaucomaAlternate ? alternateColor : defaultColor);
            leftArrow.setColorFilter(alternateColor);

            if (glaucomaOverlay.getVisibility() == View.VISIBLE) {
                fadeOutOverlay(glaucomaOverlay);
            } else {
                fadeInOverlay(glaucomaOverlay);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            rightArrow.setColorFilter(defaultColor);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            leftArrow.setColorFilter(defaultColor);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void fadeInOverlay(final View overlay) {
        overlay.setAlpha(0f);
        overlay.setVisibility(View.VISIBLE);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(overlay, "alpha", 0f, 1f);
        fadeIn.setDuration(ANIM_DURATION);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.start();
    }

    private void fadeOutOverlay(final View overlay) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(overlay, "alpha", overlay.getAlpha(), 0f);
        fadeOut.setDuration(ANIM_DURATION);
        fadeOut.setInterpolator(new DecelerateInterpolator());
        fadeOut.start();
        fadeOut.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull android.animation.Animator animator) { }
            @Override
            public void onAnimationEnd(@NonNull android.animation.Animator animator) {
                overlay.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(@NonNull android.animation.Animator animator) { }
            @Override
            public void onAnimationRepeat(@NonNull android.animation.Animator animator) { }
        });
    }
}