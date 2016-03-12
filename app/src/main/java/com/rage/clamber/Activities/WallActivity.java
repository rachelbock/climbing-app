package com.rage.clamber.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rage.clamber.Fragments.ActionBarFragment;
import com.rage.clamber.R;

/**
 * The WallActivity contains data about each wall in the climbing gym.
 */
public class WallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        //Sets ActionBar to top of activity screen.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wall_page_frame_layout, ActionBarFragment.newInstance());
        transaction.commit();
    }

    //TODO: Complete design of activity.
}
