package com.rage.clamber.Fragments.HomeActivity.Walls;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A dialog fragment for the comment to be added.
 */
public class AddCommentDialogFragment extends DialogFragment {


    @Bind(R.id.add_comment_edit_text)
    protected EditText addCommentEditText;

    public AddCommentDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_comment_dialog, null);
        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle(getString(R.string.enter_comment_ere))
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String commentText = addCommentEditText.getText().toString().trim();
                        if (commentText.length()>0){
                            //Gets the Target Fragment and calls onCommentAdded with the comment.
                            ((ClimbDetailFragment)getTargetFragment()).onCommentAdded(commentText);
                        }
                        else{
                            Toast.makeText(getContext(), "Enter a comment to have it post.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .create();

        return dialog;
    }
}
