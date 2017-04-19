package com.igordubrovin.tfsmsg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 26.03.2017.
 */

public class AboutFragment extends Fragment {

    TextView tvAppNameSummary;
    TextView tvAppNameTitle;
    TextView tvCourseNameSummary;
    TextView tvCoursesTitle;
    TextView tvDevNameSummary;
    TextView tvDeveloperTitle;
    TextView tvVersionSummary;
    TextView tvVersionTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        tvAppNameSummary = (TextView) view.findViewById(R.id.tv_app_name_summary);
        tvAppNameTitle = (TextView) view.findViewById(R.id.tv_app_name_title);
        tvCourseNameSummary = (TextView) view.findViewById(R.id.tv_course_name_summary);
        tvCoursesTitle = (TextView) view.findViewById(R.id.tv_courses_title);
        tvDevNameSummary = (TextView) view.findViewById(R.id.tv_dev_name_summary);
        tvDeveloperTitle = (TextView) view.findViewById(R.id.tv_developer_title);
        tvVersionSummary = (TextView) view.findViewById(R.id.tv_version_summary);
        tvVersionTitle = (TextView) view.findViewById(R.id.tv_version_title);
        return view;
    }



}
