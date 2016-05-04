package com.rage.clamber.Fragments.LoginActivity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rage.clamber.Activities.LoginActivity;
import com.rage.clamber.Data.User;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to display a dialog prompting for user information.
 */
public class NewUserDialogFragment extends DialogFragment {


    @Bind(R.id.user_info_dialog_fragment_name_edit_text)
    public EditText nameEditText;
    @Bind(R.id.user_info_dialog_fragment_height_ft_edit_text)
    public EditText heightFtEditText;
    @Bind(R.id.user_info_dialog_fragment_height_inches_edit_text)
    public EditText heightInEditText;
    @Bind(R.id.user_info_dialog_fragment_skill_edit_text)
    public EditText skillEditText;
    public static final int INVALID_DATA = -8000;
    public static final int MIN_SKILL = 0;
    public static final int MAX_SKILL = 12;


    public NewUserDialogFragment() {
        // Required empty public constructor
    }


    /**
     * Method to inflate and create a dialog window. Method is called from within the
     * UserInfoFragment to gather general user information.
     *
     * @return returns the Dialog fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_info_dialog, null);

        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle(getString(R.string.user_informaiton))
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isEditTextEmpty(nameEditText) || isEditTextEmpty(heightFtEditText) || isEditTextEmpty(heightInEditText) || isEditTextEmpty(skillEditText)) {
                            Toast.makeText(getContext(), R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
                        } else if (getSkillLevel(skillEditText.getText().toString()) == INVALID_DATA) {
                            Toast.makeText(getContext(), R.string.enter_valid_skill_level, Toast.LENGTH_SHORT).show();
                        } else {
                            String userName = nameEditText.getText().toString();
                            int userHeightFeet = Integer.parseInt(heightFtEditText.getText().toString());
                            int userHeightInches = Integer.parseInt(heightInEditText.getText().toString());
                            int userHeight = ((userHeightFeet * 12) + userHeightInches);
                            int userSkill = (getSkillLevel(skillEditText.getText().toString()));
                            User newUser = new User(userName, userHeight, userSkill);

                            ((LoginActivity) getActivity()).newUserPositiveClick(newUser);
                        }
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

    protected boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
    /**
     * Method to determine whether the user input skill level is valid.
     * @param skillLevel - the user intput string
     * @return - an integer value that will be stored in the database. If the string is not valid
     * it returns a defined invalid int to check for.
     */
    protected int getSkillLevel(String skillLevel) {

        int userSkill = INVALID_DATA;

        if (skillLevel.equals("b") || skillLevel.equals("B")) {
            userSkill = -1;
        } else if (TextUtils.isDigitsOnly(skillLevel)) {

            if (Integer.parseInt(skillLevel) >= MIN_SKILL && Integer.parseInt(skillLevel) <= MAX_SKILL) {
                userSkill = Integer.parseInt(skillLevel);
            }
        }

        return userSkill;
    }
}
