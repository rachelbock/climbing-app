package com.rage.clamber.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rage.clamber.Fragments.ActionBarFragment;
import com.rage.clamber.Fragments.RecommendationsFragment;
import com.rage.clamber.Fragments.UserInfoDialogFragment;
import com.rage.clamber.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The UserInfoActivity contains information about the user and recommendations based off of
 * information they provide about themselves and their climbing preferences.
 */
public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

       //Sets ActionBar to top of activity screen.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.user_info_frame_layout, ActionBarFragment.newInstance());
        transaction.replace(R.id.user_activity_recommendations_frame_layout, RecommendationsFragment.newInstance());
        transaction.commit();

    }

    /**
     * When the User Info Button is clicked, opens a dialog fragment to collect user information.
     */

    @OnClick(R.id.user_activity_user_info_button)
    public void onClickButtonClicked(Button button) {
        UserInfoDialogFragment fragment = new UserInfoDialogFragment();
        fragment.show(getSupportFragmentManager(), "dialog");
    }
}
