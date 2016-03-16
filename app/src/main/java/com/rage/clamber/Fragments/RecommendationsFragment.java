package com.rage.clamber.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rage.clamber.R;

import butterknife.ButterKnife;

/**
 * This fragment will display climb recommendations for the user.
 */
public class RecommendationsFragment extends Fragment {

    public RecommendationsFragment(){
        //Required public empty constructor
    }

    public static RecommendationsFragment newInstance() {
        RecommendationsFragment fragment = new RecommendationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recommendations, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    //TODO: Call climbs fragment when userinfo has been provided based off of preferences.
}
