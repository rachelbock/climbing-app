package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rage.clamber.R;

/**
 * Displays users projects.
 */
public class ProjectsFragment extends Fragment {


    public ProjectsFragment() {
        // Required empty public constructor
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

}
