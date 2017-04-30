package com.igordubrovin.tfsmsg.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.utils.ImageAnimation;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Игорь on 26.03.2017.
 */

public class AboutFragment extends Fragment {

    private ConstraintLayout clRootTv;
    private TextView tvAppNameSummary;
    private TextView tvAppNameTitle;
    private TextView tvCourseNameSummary;
    private TextView tvCoursesTitle;
    private TextView tvDevNameSummary;
    private TextView tvDeveloperTitle;
    private TextView tvVersionSummary;
    private TextView tvVersionTitle;
    private boolean visibilityTv;
    private GridLayout clRootIv;
    private ImageView ivKeyboard;
    private ImageView ivEmoji;
    private ImageView ivChat;
    private ImageView ivMess;
    private ImageView ivSend;
    private ImageView ivCancel;
    private List<ImageView> imageViews;
    private boolean visibilityIv;
    private int idVisibleIv;
    private ImageAnimation imageAnimation;
    private int orientationScreen;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orientationScreen = getActivity().getResources().getConfiguration().orientation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageAnimation = new ImageAnimation();
        imageViews = new LinkedList<>();
        if (savedInstanceState != null){
            visibilityTv = savedInstanceState.getBoolean(ProjectConstants.TV_STATE_VISIBLE);
            visibilityIv = savedInstanceState.getBoolean(ProjectConstants.IV_STATE_VISIBLE);
            idVisibleIv = savedInstanceState.getInt(ProjectConstants.ID_VISIBLE_IV);
        } else {
            visibilityTv = true;
            visibilityIv = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initTextView(view);
        initImageView(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ProjectConstants.TV_STATE_VISIBLE, visibilityTv);
        outState.putBoolean(ProjectConstants.IV_STATE_VISIBLE, visibilityIv);
        outState.putInt(ProjectConstants.ID_VISIBLE_IV, idVisibleIv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (imageAnimation.getImageView() != null)
            imageAnimation.stopAnimation();
    }

    private void initTextView(View view){
        clRootTv = (ConstraintLayout) view.findViewById(R.id.cl_root_tv);
        clRootTv.setOnClickListener(clickRootTv);
        tvAppNameSummary = (TextView) view.findViewById(R.id.tv_app_name_summary);
        tvAppNameTitle = (TextView) view.findViewById(R.id.tv_app_name_title);
        tvCourseNameSummary = (TextView) view.findViewById(R.id.tv_course_name_summary);
        tvCoursesTitle = (TextView) view.findViewById(R.id.tv_courses_title);
        tvDevNameSummary = (TextView) view.findViewById(R.id.tv_dev_name_summary);
        tvDeveloperTitle = (TextView) view.findViewById(R.id.tv_developer_title);
        tvVersionSummary = (TextView) view.findViewById(R.id.tv_version_summary);
        tvVersionTitle = (TextView) view.findViewById(R.id.tv_version_title);
        if (!visibilityTv){
            setVisibilityTv(View.GONE);
        }
    }

    private View.OnClickListener clickRootTv = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!visibilityTv) {
                visibilityTv = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TransitionSet transitionSet = new TransitionSet();
                    Transition slideRight = new Slide(Gravity.RIGHT)
                            .addTarget(tvAppNameSummary)
                            .addTarget(tvCourseNameSummary)
                            .addTarget(tvDevNameSummary)
                            .addTarget(tvVersionSummary)
                            .setInterpolator(new BounceInterpolator())
                            .setDuration(500);

                    Transition slideLeft = new Slide(Gravity.LEFT)
                            .addTarget(tvAppNameTitle)
                            .addTarget(tvCoursesTitle)
                            .addTarget(tvDeveloperTitle)
                            .addTarget(tvVersionTitle)
                            .setInterpolator(new BounceInterpolator())
                            .setDuration(500);

                    transitionSet.addTransition(slideLeft)
                            .addTransition(slideRight);

                    TransitionManager.beginDelayedTransition(clRootTv, transitionSet);
                } else {
                    android.support.transition.TransitionManager.beginDelayedTransition(clRootTv, new Fade());
                }
                setVisibilityTv(View.VISIBLE);
            } else {
                visibilityTv = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    android.transition.Transition explode = new Explode()
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(500);
                    android.transition.TransitionManager.beginDelayedTransition(clRootTv, explode);
                } else {
                    android.support.transition.TransitionManager.beginDelayedTransition(clRootTv, new Fade());
                }
                setVisibilityTv(View.GONE);
            }
        }
    };

    private void setVisibilityTv(int visibility){
        tvAppNameTitle.setVisibility(visibility);
        tvCoursesTitle.setVisibility(visibility);
        tvDeveloperTitle.setVisibility(visibility);
        tvVersionTitle.setVisibility(visibility);
        tvAppNameSummary.setVisibility(visibility);
        tvCourseNameSummary.setVisibility(visibility);
        tvDevNameSummary.setVisibility(visibility);
        tvVersionSummary.setVisibility(visibility);
    }

    private void initImageView(View view){
        clRootIv = (GridLayout) view.findViewById(R.id.cl_root_iv);
        ivKeyboard = (ImageView) view.findViewById(R.id.iv_keyboard);
        imageViews.add(ivKeyboard);
        ivEmoji = (ImageView) view.findViewById(R.id.iv_emoji);
        imageViews.add(ivEmoji);
        ivChat = (ImageView) view.findViewById(R.id.iv_chat);
        imageViews.add(ivChat);
        ivMess = (ImageView) view.findViewById(R.id.iv_mess);
        imageViews.add(ivMess);
        ivSend = (ImageView) view.findViewById(R.id.iv_send);
        imageViews.add(ivSend);
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        imageViews.add(ivCancel);

        ivKeyboard.setOnClickListener(clickImage);
        ivEmoji.setOnClickListener(clickImage);
        ivChat.setOnClickListener(clickImage);
        ivMess.setOnClickListener(clickImage);
        ivSend.setOnClickListener(clickImage);
        ivCancel.setOnClickListener(clickImage);

        if (!visibilityIv){
            setImageVisibility(idVisibleIv, View.GONE);
            setClickableImage(idVisibleIv, false);
            clRootIv.getViewTreeObserver().addOnGlobalLayoutListener(oneImageOnGlobalLayoutListener);
        } else {
            clRootIv.getViewTreeObserver().addOnGlobalLayoutListener(allImageOnGlobalLayoutListener);
        }
    }

    ViewTreeObserver.OnGlobalLayoutListener oneImageOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            clRootIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            ImageView imageViewAnim = getImageViewById(idVisibleIv);
            if (imageViewAnim != null) {
                imageViewAnim.setLayoutParams(getImageViewParentParam(imageViewAnim));
                imageAnimation.setImageView(imageViewAnim);
                imageAnimation.startAnimation();
            }
            setImageViewsParam(imageViewAnim);
        }
    };

    ViewTreeObserver.OnGlobalLayoutListener allImageOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            clRootIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            setImageViewsParam(null);
        }
    };

    private void setImageViewsParam(ImageView imageViewAnim){
        for (ImageView imageView : imageViews){
            if (imageView != imageViewAnim)
                imageView.setLayoutParams(getImageViewParam(imageView));
        }
    }

    private View.OnClickListener clickImage = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            idVisibleIv = v.getId();
            android.support.transition.TransitionSet transitionSet = new android.support.transition.TransitionSet();
            android.support.transition.Transition changeBounds = new ChangeBounds();
            Fade fade = new Fade();
            fade.excludeTarget(v, true);
            transitionSet.addTransition(fade)
                    .addTransition(changeBounds)
                    .setOrdering(android.support.transition.TransitionSet.ORDERING_TOGETHER)
                    .setDuration(1000);
            android.support.transition.TransitionManager.beginDelayedTransition(clRootIv, transitionSet);

            if (visibilityIv) {
                visibilityIv = false;
                setClickableImage(idVisibleIv, false);
                imageAnimation.setImageView((ImageView) v);
                setImageVisibility(idVisibleIv, View.GONE);
                v.setLayoutParams(getImageViewParentParam(v));
                imageAnimation.startAnimation();
            } else {
                visibilityIv = true;
                setClickableImage(idVisibleIv, true);
                setImageVisibility(idVisibleIv, View.VISIBLE);
                v.setLayoutParams(getImageViewParam(v));
                imageAnimation.stopAnimation();
            }
        }
    };

    private GridLayout.LayoutParams getImageViewParam(View view){
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.width = clRootIv.getWidth()/2;
        params.height = clRootIv.getHeight()/3;
        return params;
    }

    private GridLayout.LayoutParams getImageViewParentParam(View view){
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.width = clRootIv.getWidth();
        params.height = clRootIv.getHeight();
        return params;
    }

    private ImageView getImageViewById(int id){
        for (ImageView imageView : imageViews){
            if (id == imageView.getId())
                return imageView;
        }
        return null;
    }

    private void setImageVisibility(int id, int visibility){
        for (ImageView imageView : imageViews){
            if (id != imageView.getId())
                imageView.setVisibility(visibility);
        }
    }

    private void setClickableImage(int id, boolean clickable){
        for (ImageView imageView : imageViews){
            if (id != imageView.getId())
                imageView.setClickable(clickable);
        }
    }

}
