package com.example.exacuity.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.exacuity.R;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private float currentScale = 1.0f;
    private final float SCALE_STEP = 0.2f;
    private final long ANIM_DURATION = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.image_view);

        // Retrieve the image resource passed as an extra
        int imageResId = getIntent().getIntExtra("image_source", R.drawable.amsler);
        imageView.setImageResource(imageResId);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                increaseScale();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                decreaseScale();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void increaseScale() {
        float MAX_SCALE = 1.4f;
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