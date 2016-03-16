package com.rage.clamber.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Activities.ProjectsActivity;
import com.rage.clamber.Activities.UserInfoActivity;
import com.rage.clamber.Activities.WallActivity;
import com.rage.clamber.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ActionBar Fragment to set up the 4 main action buttons.
 */
public class ActionBarFragment extends Fragment {


    public ActionBarFragment() {
        // Required empty public constructor
    }

    public static ActionBarFragment newInstance () {
        return new ActionBarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_action_bar, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    /**
     *On Click methods to launch each activity when the corresponding button is pressed.
     */

    @OnClick(R.id.action_bar_home_button)
    public void onHomeButtonClicked(Button button) {
        Intent intent = new Intent(getActivity(), HomePage.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_bar_walls_button)
    public void onWallsButtonClicked(Button button) {
        Intent intent = new Intent(getActivity(), WallActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_bar_projects_button)
    public void onProjectsButtonClicked(Button button) {
        Intent intent = new Intent(getActivity(), ProjectsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_bar_user_info_button)
    public void onUserInfoButtonClicked(Button button) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }
}
