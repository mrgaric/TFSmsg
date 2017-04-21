package com.igordubrovin.tfsmsg.utils;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

/**
 * Created by Игорь on 21.04.2017.
 */

public class ImageAnimation {

    private AnimatedVectorDrawable avd;
    public ImageView imageView;

    public ImageAnimation(ImageView imageView){
        this.imageView = imageView;
        Drawable drawable = imageView.getDrawable();
        avd = (AnimatedVectorDrawable) drawable;

    }

    public void startAnimation(){
        imageView.post(runnable);
    };

    public void stopAnimation(){
        imageView.removeCallbacks(runnable);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                avd.start();
                imageView.postDelayed(runnable, 2000);
            }
        }
    };
}
