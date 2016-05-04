package com.rage.clamber.Fragments.LoginActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rage.clamber.Activities.LoginActivity;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dialog Fragment which pops up on login screen when existing user button is clicked. Allows
 * input of username and calls Async Task.
 */
public class ExistingUserLoginFragment extends DialogFragment {

    @Bind(R.id.existing_user_fragment_name_edit_text)
    protected EditText userName;

    public ExistingUserLoginFragment() {
        //Empty Constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_existing_user_login, null);

        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle("User Name:")
                .setPositiveButton(getString(R.string.login), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((LoginActivity)getActivity()).existingUserPositiveClick(userName.getText().toString().trim());
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();

        return dialog;
    }


}
