package com.borges.exacuity.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.borges.exacuity.R;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton leftArrow;
    private ImageButton rightArrow;
    private int defaultColor;
    private int alternateColor;
    private float currentScale;
    private final float SCALE_STEP = 0.2f;
    private final long ANIM_DURATION = 250;
    private boolean isTemplate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.image_view);
        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);

        Resources res = getResources();
        defaultColor = res.getColor(R.color.title_color);
        alternateColor = res.getColor(R.color.button_pressed_color);

        // Retrieve the image resource passed as an extra
        int imageResId = getIntent().getIntExtra("image_source", R.drawable.amsler);
        imageView.setImageResource(imageResId);

        if (imageResId == R.drawable.template) isTemplate = true;
        currentScale = !isTemplate ? 1.0f : 2.4f;

        imageView.setScaleX(currentScale);
        imageView.setScaleY(currentScale);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                rightArrow.setColorFilter(alternateColor);
                increaseScale();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                leftArrow.setColorFilter(alternateColor);
                decreaseScale();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
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

    private void increaseScale() {
        float MAX_SCALE = !isTemplate ? 1.4f : 2.8f;
        if (currentScale < MAX_SCALE) {
            currentScale += SCALE_STEP;
            imageView.animate()
                    .scaleX(currentScale)
                    .scaleY(currentScale)
                    .setDuration(ANIM_DURATION)
                    .start();
        }
    }

    private void decreaseScale() {
        float MIN_SCALE = 0.5f;
        if (currentScale > MIN_SCALE) {
            currentScale -= SCALE_STEP;
            imageView.animate()
                    .scaleX(currentScale)
                    .scaleY(currentScale)
                    .setDuration(ANIM_DURATION)
                    .start();
        }
    }
}