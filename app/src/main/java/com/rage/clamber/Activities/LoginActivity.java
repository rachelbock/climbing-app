package com.rage.clamber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rage.clamber.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that is called on launch of the app. Allows a user to create or enter a username that will
 * be tied to their data.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rage.clamber.R.layout.activity_login);
        ButterKnife.bind(this);
    }

    /**
     *Submit buttons launch the Home Page activity.
     */

    //TODO: submit new user button will update external database with new user.
    //TODO: submit existing user button will call external database and update app with users data.

    @OnClick(R.id.login_activity_submit_new_user_button)
    public void onNewUserButtonClicked (Button button) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_activity_submit_already_user_button)
    public void onExistingUserButtonClicked (Button button) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }


}
