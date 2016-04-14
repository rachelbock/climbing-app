package com.rage.clamber.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Adapters.ClimbsRecyclerViewAdapter;
import com.rage.clamber.Adapters.CommentsRecyclerViewAdapter;
import com.rage.clamber.Data.Comment;
import com.rage.clamber.Data.User;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.Networking.Requests.NewCommentRequest;
import com.rage.clamber.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment to hold and display user Comments for each climb.
 */
public class CommentsFragment extends Fragment {

    public static final String TAG = CommentsFragment.class.getSimpleName();
    protected User mainUser;
    protected List<Comment> comments;
    protected int climbId;
    protected CommentsRecyclerViewAdapter adapter;

    @Bind(R.id.comments_fragment_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.comments_fragment_no_comments_text)
    TextView noCommentsText;


    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance(User user, int climb_id) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        args.putInt(ClimbsRecyclerViewAdapter.ARG_CLIMB, climb_id);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, rootView);

        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        climbId = getArguments().getInt(ClimbsRecyclerViewAdapter.ARG_CLIMB);
        comments = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommentsRecyclerViewAdapter(comments, mainUser, getContext());
        recyclerView.setAdapter(adapter);

        getCommentsByClimb(climbId);

        return rootView;
    }

    /**
     * Method to make a network call to the Clamber Server to get all Comments by Climb Id. The
     * climbsRecyclerViewAdapter has an OnClick method on the comments button that launches this
     * fragment and triggers this network call to display comments.
     *
     * @param climbId - id of the climb that was selected.
     */
    public void getCommentsByClimb(int climbId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Call<List<Comment>> commentsCall = ApiManager.getClamberService().getComments(climbId);
            commentsCall.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    if (response.code() == 200) {
                        List<Comment> commentsList = response.body();
                        comments.addAll(commentsList);
                        if (comments.isEmpty()) {
                            noCommentsText.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {
                    Log.d(TAG, "Failure when attempting to return comments for climb", t);

                }
            });

        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * OnClick for the "add comment" button. Takes the text in the EditText field and Posts it to
     * the database. Prints out a log message if it is unsuccessful.
     */
    @OnClick(R.id.comments_fragment_add_comment_button)
    public void onAddCommentButtonClicked() {

        AddCommentDialogFragment dialogFragment = new AddCommentDialogFragment();
        dialogFragment.setTargetFragment(this, 0);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");

    }

    /**
     * When comment is added from the AddCommentDialogFragment this method is called to send the
     * post request to the server. After the comment has been posted it also adds the Comment
     * directly to the CommentsList and updates the adapter so the comment displays immediately.
     * @param comment - the comment string from the dialog fragment
     */
    public void onCommentAdded(String comment) {

        NewCommentRequest request = new NewCommentRequest();
        request.setClimbId(climbId);
        request.setUsername(mainUser.getUserName());
        request.setCommmentText(comment);

        DateTime now = DateTime.now(DateTimeZone.UTC);
        request.setDateText(now.getMillis());

        final Call<Boolean> addCommentCall = ApiManager.getClamberService().addComment(climbId, request);
        addCommentCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.body()) {
                    Log.d(TAG, "Successfully Added Comment");
                } else {
                    Log.d(TAG, "Unable to add comment");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d(TAG, "Unable to add comment", t);
            }
        });
        Comment newComment = new Comment();
        newComment.setClimbId(climbId);
        newComment.setComment(comment);
        newComment.setDate(request.getDateText());
        newComment.setUserName(mainUser.getUserName());

        comments.add(0, newComment);
        adapter.notifyDataSetChanged();
        noCommentsText.setVisibility(View.INVISIBLE);
    }
}