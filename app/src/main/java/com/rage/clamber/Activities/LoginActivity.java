package com.rage.clamber.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.Networking.Requests.NewUserDataRequest;
import com.rage.clamber.Data.User;
import com.rage.clamber.Fragments.ExistingUserLoginFragment;
import com.rage.clamber.Fragments.NewUserDialogFragment;
import com.rage.clamber.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity that is called on launch of the app. Allows a user to create or enter a username that will
 * be tied to their data.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String ARG_USER = "Login Activity User";
    protected String userName;
    protected User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rage.clamber.R.layout.activity_login);
        ButterKnife.bind(this);
    }

    /**
     * Submit buttons launch the Home Page activity.
     */

    @OnClick(R.id.login_activity_new_user_button)
    public void onNewUserButtonClicked(Button button) {
        NewUserDialogFragment fragment = new NewUserDialogFragment();
        fragment.show(getSupportFragmentManager(), "dialog");

    }

    @OnClick(R.id.login_activity_existing_user_button)
    public void onExistingUserButtonClicked(Button button) {
        ExistingUserLoginFragment fragment = new ExistingUserLoginFragment();
        fragment.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Method called from ExistingUserLoginFragment when a username is entered. The method makes a
     * call to the server to determine if the username exists in the database. If it does, the user
     * is logged into the application and brought to the home page. The User is stored in the Home
     * Page class in order to pull up other data related to that User in various fragments.
     *
     * @param user - username that has been entered in dialog fragment.
     */

    public void existingUserPositiveClick(String user) {
        userName = user;
        if (userName.length() == 0) {
            Toast.makeText(this, R.string.enter_username, Toast.LENGTH_SHORT).show();
        } else {
            //Check for internet connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                Call<User> userCall = ApiManager.getClamberService().getExistingUser(userName);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User user = response.body();
                        if (response.code() == 200) {
                            String login = response.body().getUserName();
                            Toast.makeText(LoginActivity.this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomePage.class);
                            intent.putExtra(ARG_USER, user);
                            startActivity(intent);
                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, R.string.invalid_username, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_unsuccessful, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d(TAG, "FAILURE: ", t);
                        Toast.makeText(LoginActivity.this, R.string.login_unsuccessful, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Method called from NewUserDialogFragment called when the New User Button is selected and new
     * user data has been entered. When the Login Button is selected, a new User object is created
     * and this method is called. This method uses the NewUserAsyncTask to make a network call on
     * the user. It checks to make sure the user does not exist and then posts the user to the
     * database.
     *
     * @param user - User object passed back from the NewUserDialogFragment
     */
    public void newUserPositiveClick(User user) {

        NewUserDataRequest request = new NewUserDataRequest();
        request.setUsername(user.getUserName());
        request.setHeight(user.getHeight());
        request.setSkill(user.getSkillLevel());

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            final Call<User> newUserCall = ApiManager.getClamberService().addNewUser(request);
            newUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        newUser = response.body();
                        Toast.makeText(LoginActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
                        intent.putExtra(ARG_USER, newUser);
                        startActivity(intent);
                    } else if (response.code() == 400) {
                        Toast.makeText(LoginActivity.this, "This username already exists. Create a new username or select 'Existing User'.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "Failed to create new user", t);

                }
            });

        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
