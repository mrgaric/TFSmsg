package com.igordubrovin.tfsmsg.adapters;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
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
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(170, 170));

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        final Drawable drawable = imageView.getDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Animatable) drawable).start();
        }
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            //here you can place the image
            R.drawable.avd_vector_anim_cancel, R.drawable.avd_vector_anim_emoji,
            R.drawable.avd_vector_anim_keyboard, R.drawable.avd_vector_anim_send
    };

}
