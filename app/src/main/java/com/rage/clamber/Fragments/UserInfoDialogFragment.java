package com.rage.clamber.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to display a dialog prompting for user information.
 */
public class UserInfoDialogFragment extends DialogFragment {


    @Bind(R.id.user_info_dialog_fragment_name_edit_text)
    EditText nameEditText;
    @Bind(R.id.user_info_dialog_fragment_gender_edit_text)
    EditText genderEditText;
    @Bind(R.id.user_info_dialog_fragment_height_edit_text)
    EditText heightEditText;
    @Bind(R.id.user_info_dialog_fragment_skill_edit_text)
    EditText skillEditText;

    public UserInfoDialogFragment() {
        // Required empty public constructor
    }


    /**
     * Method to inflate and create a dialog window. Method is called from within the
     * UserInfoFragment to gather general user information.
     * @return returns the Dialog fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_info_dialog, null);

        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle("User Information:")
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                        //TODO: Define what happens when the ok button is selected.
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Define what happens when the cancel button is selected.
                    }
                })
                .create();

        return dialog;
    }

}
