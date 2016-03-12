package com.rage.clamber.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rage.clamber.Fragments.ActionBarFragment;
import com.rage.clamber.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Sets ActionBar to top of activity screen.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, ActionBarFragment.newInstance());
        transaction.commit();

    }

}
