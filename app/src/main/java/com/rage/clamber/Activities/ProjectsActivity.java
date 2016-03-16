package com.rage.clamber.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rage.clamber.Fragments.ActionBarFragment;
import com.rage.clamber.Fragments.ClimbsFragment;
import com.rage.clamber.R;

public class ProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);


        /**
         * Sets ActionBarFragment to top of page for navigation between activities.
         * Sets the ClimbsFragment beneath the action bar fragment.
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.projects_page_frame_layout, ActionBarFragment.newInstance());
        transaction.replace(R.id.projects_page_climbs_fragment_frame, ClimbsFragment.newInstance());
        transaction.commit();
    }

}
