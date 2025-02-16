package com.example.exacuity.activities;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exacuity.R;
import com.example.exacuity.utils.ExhibitionUtils;

public class DaltonismActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView descriptionView;
    private LinearLayout indicatorLayout;
    private int currentImageIndex = 0;
    private final int totalImages = 35;

    private final int[] imageIds = new int[totalImages];
    private final String[] descriptions = new String[totalImages];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daltonism_activity);

        imageView = findViewById(R.id.image_view);
        descriptionView = findViewById(R.id.description_view);
        indicatorLayout = findViewById(R.id.indicator_layout);

        for (int i = 0; i < totalImages; i++) {
            imageIds[i] = ExhibitionUtils.ishiwaraIcons[i];
            descriptions[i] = ExhibitionUtils.ishiwaraDescriptions[i];
        }

        setupIndicators();
        updateUI();
    }

    private void setupIndicators() {
        indicatorLayout.removeAllViews();
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

        for (int i = 0; i < totalImages; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(margin, margin, margin, margin);
            dot.setLayoutParams(params);
            // Initially, all dots are unselected.
            dot.setBackgroundResource(R.drawable.indicator_unselected);
            indicatorLayout.addView(dot);
        }
    }

    private void updateIndicators() {
        int count = indicatorLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View dot = indicatorLayout.getChildAt(i);
            dot.setBackgroundResource(i == currentImageIndex ? R.drawable.indicator_selected : R.drawable.indicator_unselected);
        }
    }

    private void updateUI() {
        imageView.setImageResource(imageIds[currentImageIndex]);
        descriptionView.setText(descriptions[currentImageIndex]);
        updateIndicators();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                currentImageIndex = (currentImageIndex + 1) % totalImages;
                updateUI();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                currentImageIndex = (currentImageIndex - 1 + totalImages) % totalImages;
                updateUI();
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                descriptionView.setVisibility(descriptionView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}