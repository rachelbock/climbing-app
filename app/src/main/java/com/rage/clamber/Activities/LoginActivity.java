package com.rage.clamber.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.rage.clamber.AsyncTasks.LoginAsyncTask;
import com.rage.clamber.AsyncTasks.NewUserAsyncTask;
import com.rage.clamber.Data.User;
import com.rage.clamber.Fragments.ExistingUserLoginFragment;
import com.rage.clamber.Fragments.UserInfoDialogFragment;
import com.rage.clamber.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that is called on launch of the app. Allows a user to create or enter a username that will
 * be tied to their data.
 */

public class LoginActivity extends AppCompatActivity implements LoginAsyncTask.existingUserLoginInterface, NewUserAsyncTask.newUserLoginInterface{

    public static final String TAG = LoginActivity.class.getSimpleName();
    protected String userName;
    LoginAsyncTask loginAsyncTask;
    NewUserAsyncTask newUserAsyncTask;

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

    @OnClick(R.id.login_activity_new_user_button)
    public void onNewUserButtonClicked (Button button) {
        UserInfoDialogFragment fragment = new UserInfoDialogFragment();
        fragment.show(getSupportFragmentManager(), "dialog");

    }

    @OnClick(R.id.login_activity_existing_user_button)
    public void onExistingUserButtonClicked (Button button) {
        ExistingUserLoginFragment fragment = new ExistingUserLoginFragment();
        fragment.show(getSupportFragmentManager(), "dialog");

    }

    public void existingUserPositiveClick(String user) {
        userName = user;
        if (userName.length() == 0) {
            Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show();
        } else {
            //Check for internet connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                loginAsyncTask = new LoginAsyncTask(this);
                loginAsyncTask.execute(userName);
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void newUserPositiveClick(User user) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            newUserAsyncTask = new NewUserAsyncTask(this);
            newUserAsyncTask.execute(user);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUserNameRetrieved(@Nullable JSONObject jsonObject) {
        if (jsonObject == null) {
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                String login = jsonObject.getString("userName");
                if (userName.equals(login)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomePage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "The user name provided is not valid", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNewUserSubmittedListener(NewUserAsyncTask.CreateUserResult result) {
        if (result == NewUserAsyncTask.CreateUserResult.USER_ALREADY_EXISTS) {
            Toast.makeText(this, "This username already exists", Toast.LENGTH_SHORT).show();
        }
        else if (result == NewUserAsyncTask.CreateUserResult.CONNECTION_FAILURE) {
            Toast.makeText(this, "Connection Failure", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
    }
}
