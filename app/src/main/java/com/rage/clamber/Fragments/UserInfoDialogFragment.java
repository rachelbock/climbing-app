package com.rage.clamber.Fragments;


import android.app.Dialog;
import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
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


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_info_dialog, null);

        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle("User Information:")
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //what happens when ok is clicked?
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //what happens when cancel is clicked?
                    }
                })
                .create();

        return dialog;
    }

}
