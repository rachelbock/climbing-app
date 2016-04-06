package com.rage.clamber.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.rage.clamber.AsyncTasks.ClamberService;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Activity that is called on launch of the app. Allows a user to create or enter a username that will
 * be tied to their data.
 */

public class LoginActivity extends AppCompatActivity implements LoginAsyncTask.existingUserLoginInterface, NewUserAsyncTask.newUserLoginInterface{

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String ARG_USER = "Login Activity User";
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

    //TODO: Cleanup code to remove LoginAsyncTask as retrofit implementation makes it obsolete

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
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.103:8080/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
                ClamberService clamber = retrofit.create(ClamberService.class);
                Call<User> userCall = clamber.getExistingUser(userName);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User user = response.body();
                        if (response.code() == 200) {
                            String login = response.body().getUserName();
                            if (userName.equals(login)) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomePage.class);
                                intent.putExtra(ARG_USER, user);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "The user name provided is not valid", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d(TAG,"FAILURE: ",t);
                        Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
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

            //TODO: get user back out of this in order to set user.
        }
    }
}
