package com.igordubrovin.tfsmsg.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 18.04.2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            Point size = new Point();
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getSize(size);
            int width = size.x/2;
            int height = size.y/3;
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(width, height));
            imageView.setBackgroundColor(Color.RED);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(mThumbIds[position]);
            /*final Drawable drawable = imageView.getDrawable();*/
            /*final AnimatedVectorDrawableCompat animatedVector = AnimatedVectorDrawableCompat.create(mContext, mThumbIds[position]);
            imageView.setImageDrawable(animatedVector);
            if (animatedVector != null) {
                animatedVector.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        super.onAnimationEnd(drawable);
                        animatedVector.start();
                    }
                });
                animatedVector.start();*/
                final AnimatedVectorDrawable avd;
                final Drawable drawable = imageView.getDrawable();
                avd = (AnimatedVectorDrawable) drawable;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
                        @Override
                        public void onAnimationEnd(Drawable drawable) {
                            super.onAnimationEnd(drawable);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                avd.start();
                            }
                        }
                    });
                }
                ((AnimatedVectorDrawable) drawable).start();
            }


        } else {
            imageView = (ImageView) convertView;
        }



        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            //here you can place the image
           /* R.drawable.avd_vector_anim_cancel, R.drawable.avd_vector_anim_emoji,
            R.drawable.avd_vector_anim_keyboard, R.drawable.avd_vector_anim_send,
            R.drawable.avd_vector_mess, R.drawable.avd_vector_anim_chat*/
            R.drawable.avd_vector_anim_cancel
    };
}
