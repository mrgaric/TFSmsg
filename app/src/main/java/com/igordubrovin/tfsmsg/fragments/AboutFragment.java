package com.igordubrovin.tfsmsg.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private ConstraintLayout clRootIv;
    private ImageView ivKeyboard;
    private ImageView ivEmoji;
    private ImageView ivChat;
    private ImageView ivMess;
    private ImageView ivSend;
    private ImageView ivCancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            visibilityTv = savedInstanceState.getBoolean("tvStateVisible");
        } else {
            visibilityTv = true;
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
            tvAppNameTitle.setVisibility(View.GONE);
            tvCoursesTitle.setVisibility(View.GONE);
            tvDeveloperTitle.setVisibility(View.GONE);
            tvVersionTitle.setVisibility(View.GONE);
            tvAppNameSummary.setVisibility(View.GONE);
            tvCourseNameSummary.setVisibility(View.GONE);
            tvDevNameSummary.setVisibility(View.GONE);
            tvVersionSummary.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("tvStateVisible", visibilityTv);
    }

    private void initImageView(View view){
        clRootIv = (ConstraintLayout) view.findViewById(R.id.cl_root_iv);
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
            changeBounds.addTarget(v).addListener(transitionListener);
            fade.excludeTarget(v, true);
            transitionSet.addTransition(fade)
                    .addTransition(changeBounds)
                    .setOrdering(android.support.transition.TransitionSet.ORDERING_TOGETHER)
                    .setDuration(1000);
            android.support.transition.TransitionManager.beginDelayedTransition(clRootIv, transitionSet);

            if (v.getId() != ivKeyboard.getId()){
                ivKeyboard.setVisibility(View.GONE);
            }
            if (v.getId() != ivEmoji.getId()){
                ivEmoji.setVisibility(View.GONE);
            }
            if (v.getId() != ivChat.getId()){
                ivChat.setVisibility(View.GONE);
            }
            if (v.getId() != ivMess.getId()){
                ivMess.setVisibility(View.GONE);
            }
            if (v.getId() != ivSend.getId()){
                ivSend.setVisibility(View.GONE);
            }
            if (v.getId() != ivCancel.getId()){
                ivCancel.setVisibility(View.GONE);
            }

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
            params.width = clRootIv.getWidth();
            params.height =clRootIv.getHeight();
            params.leftMargin = 0;
            params.rightMargin = 0;
            params.bottomMargin = 0;
            params.topMargin = 0;

            v.setLayoutParams(params);
        }
    };

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
                tvAppNameTitle.setVisibility(View.VISIBLE);
                tvCoursesTitle.setVisibility(View.VISIBLE);
                tvDeveloperTitle.setVisibility(View.VISIBLE);
                tvVersionTitle.setVisibility(View.VISIBLE);
                tvAppNameSummary.setVisibility(View.VISIBLE);
                tvCourseNameSummary.setVisibility(View.VISIBLE);
                tvDevNameSummary.setVisibility(View.VISIBLE);
                tvVersionSummary.setVisibility(View.VISIBLE);
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
                tvAppNameTitle.setVisibility(View.GONE);
                tvCoursesTitle.setVisibility(View.GONE);
                tvDeveloperTitle.setVisibility(View.GONE);
                tvVersionTitle.setVisibility(View.GONE);
                tvAppNameSummary.setVisibility(View.GONE);
                tvCourseNameSummary.setVisibility(View.GONE);
                tvDevNameSummary.setVisibility(View.GONE);
                tvVersionSummary.setVisibility(View.GONE);
            }
        }
    };

    private android.support.transition.Transition.TransitionListener transitionListener = new android.support.transition.Transition.TransitionListener() {
        @Override
        public void onTransitionStart(@NonNull android.support.transition.Transition transition) {

        }

        @Override
        public void onTransitionEnd(@NonNull android.support.transition.Transition transition) {
            ImageAnimation imageAnimation = new ImageAnimation((ImageView) transition.getTargets().get(0));
            imageAnimation.startAnimation();
        }

        @Override
        public void onTransitionCancel(@NonNull android.support.transition.Transition transition) {

        }

        @Override
        public void onTransitionPause(@NonNull android.support.transition.Transition transition) {

        }

        @Override
        public void onTransitionResume(@NonNull android.support.transition.Transition transition) {

        }
    };
}
