package com.igordubrovin.tfsmsg.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.di.components.CommonComponent;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.ImageAnimation;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private final List<ImageAnimation> imageAnimations = new ArrayList<>();
    private int height;
    private int width;
    private CommonComponent commonComponent;

    private Integer[] imagesArray = {
            R.drawable.avd_vector_anim_cancel, R.drawable.avd_vector_anim_emoji,
            R.drawable.avd_vector_anim_keyboard, R.drawable.avd_vector_anim_send,
            R.drawable.avd_vector_mess, R.drawable.avd_vector_anim_chat
    };

    public ImageAdapter(Context c, int widthItem, int heightItem) {
        context = c;
        width = widthItem;
        height = heightItem;
        commonComponent = App.plusCommonComponent();
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
            imageView = createImageVIew(position);
            ImageAnimation imageAnimation = createImageAnimation(imageView);
            imageAnimations.add(imageAnimation);
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }

    private ImageView createImageVIew(int position){
        ImageView imageView = new ImageView(context);
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
        return imageView;
    }

    private ImageAnimation createImageAnimation(ImageView imageView){
        ImageAnimation imageAnimation = commonComponent.getImageAnimation();
        imageAnimation.setImageView(imageView);
        imageAnimation.startAnimation();
        return imageAnimation;
    }

    public void stopAnimation(){
        for (ImageAnimation animation : imageAnimations){
            if (animation.getImageView() != null)
                animation.stopAnimation();
        }
    }
}
