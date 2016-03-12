package com.rage.clamber.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rage.clamber.Fragments.ActionBarFragment;
import com.rage.clamber.R;

public class ProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);


        /**
         * Sets ActionBarFragment to top of page for navigation between activities.
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.projects_page_frame_layout, ActionBarFragment.newInstance());
        transaction.commit();
    }


}
