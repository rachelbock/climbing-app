package com.rage.clamber.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.AsyncTasks.LoginAsyncTask;
import com.rage.clamber.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dialog Fragment which pops up on login screen when existing user button is clicked. Allows
 * input of username and calls Async Task.
 */
public class ExistingUserLoginFragment extends DialogFragment implements LoginAsyncTask.existingUserLoginInterface {

    LoginAsyncTask asyncTask;
    @Bind(R.id.existing_user_fragment_name_edit_text)
    EditText userName;

    public ExistingUserLoginFragment() {
        //Empty Constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_existing_user_login, null);

        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle("User Name:")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Check for internet connectivity
                        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            
                            asyncTask = new LoginAsyncTask();
                            asyncTask.execute(userName.getText().toString());
                        }
                        else {
                            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                            }
                })
                .create();

        return dialog;
    }


    @Override
    public void onUserNameRetrieved(@Nullable JSONObject jsonObject) {
        if (jsonObject == null) {
            Toast.makeText(getContext(), "The user name provided is not valid", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                String login = jsonObject.getString("userName");
                if (login.equals(userName)) {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this.getActivity(), HomePage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "The user name provided is not valid", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
