package com.rage.clamber.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rage.clamber.Data.User;
import com.rage.clamber.Data.UserSQLiteHelper;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to display a dialog prompting for user information.
 */
public class UserInfoDialogFragment extends DialogFragment {

    private UserSQLiteHelper userSQLiteHelper;

    @Bind(R.id.user_info_dialog_fragment_name_edit_text)
    EditText nameEditText;
    @Bind(R.id.user_info_dialog_fragment_height_ft_edit_text)
    EditText heightFtEditText;
    @Bind(R.id.user_info_dialog_fragment_height_inches_edit_text)
    EditText heightInEditText;
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

                                String userName = nameEditText.getText().toString();
                                int userHeightFeet = Integer.parseInt(heightFtEditText.getText().toString());
                                int userHeightInches = Integer.parseInt(heightInEditText.getText().toString());
                                int userHeight = ((userHeightFeet * 12) + userHeightInches);
                                int userSkill = Integer.parseInt(skillEditText.getText().toString());
                                User user = new User(userName, userHeight, userSkill);
                                user.setId(1);
                                userSQLiteHelper = UserSQLiteHelper.getInstance(getActivity().getApplicationContext());

                                userSQLiteHelper.getWritableDatabase().insert(User.TABLE_NAME, null, user.getContentValues());
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You can update your user information at any time by selecting the User Info button", Toast.LENGTH_LONG).show();
                    }
                })
                .create();

        return dialog;
    }

}
