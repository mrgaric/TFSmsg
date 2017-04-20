package com.igordubrovin.tfsmsg.adapters;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.igordubrovin.tfsmsg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 18.04.2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Thread> threadList = new ArrayList<>();

    // Keep all Images in array
    private Integer[] imagesArray = {
            //here you can place the image
            R.drawable.avd_vector_anim_cancel, R.drawable.avd_vector_anim_emoji,
            R.drawable.avd_vector_anim_keyboard, R.drawable.avd_vector_anim_send,
            R.drawable.avd_vector_mess, R.drawable.avd_vector_anim_chat
    };

    public ImageAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return imagesArray.length;
    }

    public Object getItem(int position) {
        return imagesArray[position];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

        if (convertView == null) {
            Point size = new Point();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getSize(size);
            int width = size.x/2;
            int height = size.y/3;
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(width, height));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(imagesArray[position]);
            switch (position){
                case 0: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_first_image));
                    break;
                case 1: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_second_image));
                    break;
                case 2: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_third_image));
                    break;
                case 3: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_fourth_image));
                    break;
                case 4: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_fifth_image));
                    break;
                case 5: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_sixth_image));
                    break;
                default: imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_default_image));
                    break;
            }

            final AnimatedVectorDrawable avd;
            Drawable drawable = imageView.getDrawable();
            avd = (AnimatedVectorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                avd.start();
            }

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(2000);
                            if (Thread.currentThread().isInterrupted())
                                break;
                            imageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        avd.start();
                                    }
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            threadList.add(thread);
            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        avd.start();
                    }
                }
            });*/

        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }

    public void interruptThreads(){
        for (Thread t : threadList){
            t.interrupt();
        }
    }
}
