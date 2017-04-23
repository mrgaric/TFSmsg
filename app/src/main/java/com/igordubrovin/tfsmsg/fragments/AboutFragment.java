package com.igordubrovin.tfsmsg.fragments;

import android.content.pm.ActivityInfo;
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
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.utils.ImageAnimation;

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
    private boolean visibilityIv;
    private ImageAnimation imageAnimation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageAnimation = new ImageAnimation();
        if (savedInstanceState != null){
            visibilityTv = savedInstanceState.getBoolean("tvStateVisible");
            visibilityIv = savedInstanceState.getBoolean("tvStateVisible");
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
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_USER);
        if (imageAnimation.getImageView() != null)
            imageAnimation.stopAnimation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("tvStateVisible", visibilityTv);
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
                            .addTarget(tvAppNameTitle)
                            .addTarget(tvCoursesTitle)
                            .addTarget(tvDeveloperTitle)
                            .addTarget(tvVersionTitle)
                            .addTarget(tvAppNameSummary)
                            .addTarget(tvCourseNameSummary)
                            .addTarget(tvDevNameSummary)
                            .addTarget(tvVersionSummary)
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

    private void initImageView(View view){
        clRootIv = (GridLayout) view.findViewById(R.id.cl_root_iv);
        ivKeyboard = (ImageView) view.findViewById(R.id.iv_keyboard);
        ivEmoji = (ImageView) view.findViewById(R.id.iv_emoji);
        ivChat = (ImageView) view.findViewById(R.id.iv_chat);
        ivMess = (ImageView) view.findViewById(R.id.iv_mess);
        ivSend = (ImageView) view.findViewById(R.id.iv_send);
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);

        ivKeyboard.setOnClickListener(clickImage);
        ivEmoji.setOnClickListener(clickImage);
        ivChat.setOnClickListener(clickImage);
        ivMess.setOnClickListener(clickImage);
        ivSend.setOnClickListener(clickImage);
        ivCancel.setOnClickListener(clickImage);
    }

    private View.OnClickListener clickImage = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

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
                setClickableImage(v, false);
                imageAnimation.setImageView((ImageView) v);
                setImageVisibility(v, View.GONE);
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) v.getLayoutParams();
                params.width = clRootIv.getWidth();
                params.height = clRootIv.getHeight();
                params.leftMargin = 0;
                v.setLayoutParams(params);
                imageAnimation.startAnimation();
            } else {
                visibilityIv = true;
                setClickableImage(v, true);
                setImageVisibility(v, View.VISIBLE);
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) v.getLayoutParams();
                params.width = 360;
                params.height = 360;
                params.leftMargin = 90;
                v.setLayoutParams(params);
                imageAnimation.stopAnimation();
            }
        }
    };

    private void setImageVisibility(View v, int visibility){
        if (v.getId() != ivKeyboard.getId()){
            ivKeyboard.setVisibility(visibility);
        }
        if (v.getId() != ivEmoji.getId()){
            ivEmoji.setVisibility(visibility);
        }
        if (v.getId() != ivChat.getId()){
            ivChat.setVisibility(visibility);
        }
        if (v.getId() != ivMess.getId()){
            ivMess.setVisibility(visibility);
        }
        if (v.getId() != ivSend.getId()){
            ivSend.setVisibility(visibility);
        }
        if (v.getId() != ivCancel.getId()){
            ivCancel.setVisibility(visibility);
        }
    }

    private void setClickableImage(View v, boolean clickable){
        if (v.getId() != ivKeyboard.getId()){
            ivKeyboard.setClickable(clickable);
        }
        if (v.getId() != ivEmoji.getId()){
            ivEmoji.setClickable(clickable);
        }
        if (v.getId() != ivChat.getId()){
            ivChat.setClickable(clickable);
        }
        if (v.getId() != ivMess.getId()){
            ivMess.setClickable(clickable);
        }
        if (v.getId() != ivSend.getId()){
            ivSend.setClickable(clickable);
        }
        if (v.getId() != ivCancel.getId()){
            ivCancel.setClickable(clickable);
        }
    }

}
