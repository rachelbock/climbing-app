package com.rage.clamber.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rage.clamber.Data.User;
import com.rage.clamber.Fragments.HomeFragment;
import com.rage.clamber.Fragments.ProjectsFragment;
import com.rage.clamber.Fragments.UserInfoFragment;
import com.rage.clamber.Fragments.WallsFragment;
import com.rage.clamber.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The HomePage is the first page of the app. It contains an action bar for navigation to each
 * of the activities.
 */
public class HomePage extends AppCompatActivity {

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        user=getIntent().getParcelableExtra(LoginActivity.ARG_USER);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, HomeFragment.newInstance());
        transaction.commit();

    }

    @OnClick(R.id.action_bar_home_button)
    public void onHomeButtonClicked(Button button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, HomeFragment.newInstance());
        transaction.commit();
    }

    @OnClick(R.id.action_bar_walls_button)
    public void onWallsButtonClicked(Button button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallsFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @OnClick(R.id.action_bar_projects_button)
    public void onProjectsButtonClicked(Button button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, ProjectsFragment.newInstance(user));
        transaction.addToBackStack(null);
        transaction.commit();
        //TODO: Work in projects fragment - will eventually have to pass through the correct climbs.
    }

    @OnClick(R.id.action_bar_user_info_button)
    public void onUserInfoButtonClicked(Button button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, UserInfoFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //TODO: Deal with backstack/on back pressed reaction
}
