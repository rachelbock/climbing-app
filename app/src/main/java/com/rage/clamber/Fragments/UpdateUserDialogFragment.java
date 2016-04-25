package com.rage.clamber.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Data.User;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * DialogFragment for updating user information
 */
public class UpdateUserDialogFragment extends DialogFragment {

    @Bind(R.id.update_user_dialog_fragment_height_ft_edit_text)
    EditText heightFtEditText;
    @Bind(R.id.update_user_dialog_fragment_height_inches_edit_text)
    EditText heightInEditText;
    @Bind(R.id.update_user_dialog_fragment_skill_edit_text)
    EditText skillEditText;
    protected User mainUser;
    public UpdateUserDialogFragment() {
        // Required empty public constructor
    }

    public static UpdateUserDialogFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        UpdateUserDialogFragment fragment = new UpdateUserDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_update_user_dialog, null);
        ButterKnife.bind(this, rootView);
        mainUser = getArguments().getParcelable(HomePage.ARG_USER);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle("Update Your Information:")
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isEditTextEmpty(heightFtEditText) || isEditTextEmpty(heightInEditText) || isEditTextEmpty(skillEditText)) {
                            Toast.makeText(getContext(), "Must complete all fields to update profile.", Toast.LENGTH_SHORT).show();

                        }
                        else if (Integer.parseInt(heightInEditText.getText().toString()) > 12) {
                            Toast.makeText(getContext(), "Invalid Inch Amount", Toast.LENGTH_SHORT).show();
                        } else {
                            int userHeightFeet = Integer.parseInt(heightFtEditText.getText().toString());
                            int userHeightInches = Integer.parseInt(heightInEditText.getText().toString());
                            int userHeight = ((userHeightFeet * 12) + userHeightInches);
                            int userSkill = Integer.parseInt(skillEditText.getText().toString());
                            User user = new User(mainUser.getUserName(), userHeight, userSkill);
                            //gets the target fragment and calls onUserUpdate with the user.
                            ((UserInfoFragment) getTargetFragment()).onUserUpdate(user);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .create();
        return dialog;
    }


    public boolean isEditTextEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }
}
