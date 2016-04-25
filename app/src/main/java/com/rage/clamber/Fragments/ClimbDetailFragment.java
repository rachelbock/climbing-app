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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Adapters.ClimbsRecyclerViewAdapter;
import com.rage.clamber.Adapters.CommentsRecyclerViewAdapter;
import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.Comment;
import com.rage.clamber.Data.User;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.Networking.Requests.NewCommentRequest;
import com.rage.clamber.Networking.Requests.UserRatingRequest;
import com.rage.clamber.R;
import com.squareup.picasso.Picasso;

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
 * A fragment to hold and display user Comments and Details for each climb.
 */
public class ClimbDetailFragment extends Fragment {

    public static final String TAG = ClimbDetailFragment.class.getSimpleName();
    public static final int NO_DATA = -2;

    protected User mainUser;
    protected List<Comment> comments;
    protected Climb climb;
    protected CommentsRecyclerViewAdapter adapter;
    @Bind(R.id.comments_fragment_recycler_view)
    protected RecyclerView recyclerView;
    @Bind(R.id.comments_fragment_no_comments_text)
    protected TextView noCommentsText;
    @Bind(R.id.comments_fragment_wall_image)
    protected ImageView wallImage;
    @Bind(R.id.comments_fragment_project_star)
    protected ImageView projectStar;
    @Bind(R.id.comments_fragment_completed_check)
    protected ImageView completedCheck;
    @Bind(R.id.comments_fragment_gym_rating_text_view)
    protected TextView gymRatingTextView;
    @Bind(R.id.comments_fragment_user_rating_text_view)
    protected TextView userRatingTextView;
    @Bind(R.id.comments_fragment_your_rating_text_view)
    protected TextView yourRatingTextView;
    protected int yourRatingInt = NO_DATA;
    protected double userAvgRating = NO_DATA;
    @Bind(R.id.comments_fragment_up_arrow)
    protected ImageButton upArrow;
    @Bind(R.id.comments_fragment_down_arrow)
    protected ImageButton downArrow;

    public ClimbDetailFragment() {
        // Required empty public constructor
    }

    public static ClimbDetailFragment newInstance(User user, Climb climb) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        args.putParcelable(ClimbsRecyclerViewAdapter.ARG_CLIMB, climb);
        ClimbDetailFragment fragment = new ClimbDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_climb_detail, container, false);
        ButterKnife.bind(this, rootView);

        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        climb = getArguments().getParcelable(ClimbsRecyclerViewAdapter.ARG_CLIMB);
        comments = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommentsRecyclerViewAdapter(comments, mainUser, getContext());
        recyclerView.setAdapter(adapter);

        if (climb.isProject()) {
            projectStar.setVisibility(View.VISIBLE);
        }
        if (climb.isCompleted()) {
            completedCheck.setVisibility(View.VISIBLE);
        }

        gymRatingTextView.setText(getContext().getString(R.string.gym_rating_s, climb.getGymRating()));
        getYourRating(climb.getClimbId(), mainUser.getUserName());
        getAvgUserRatingForClimb(climb.getClimbId());
        getCommentsByClimb(climb.getClimbId());
        Picasso.with(getActivity()).load(ApiManager.getImageUrl("assets/wall_sections/" + climb.getWallId() + ".jpg")).into(wallImage);
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
        //Set Target Fragment creates a callback to this fragment from the DialogFragment
        dialogFragment.setTargetFragment(this, 0);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");

    }

    /**
     * When comment is added from the AddCommentDialogFragment this method is called to send the
     * post request to the server. After the comment has been posted it also adds the Comment
     * directly to the CommentsList and updates the adapter so the comment displays immediately.
     *
     * @param comment - the comment string from the dialog fragment
     */
    public void onCommentAdded(String comment) {

        NewCommentRequest request = new NewCommentRequest();
        request.setClimbId(climb.getClimbId());
        request.setUsername(mainUser.getUserName());
        request.setCommmentText(comment);

        DateTime now = DateTime.now(DateTimeZone.UTC);
        request.setDate(now.getMillis());

        final Call<Boolean> addCommentCall = ApiManager.getClamberService().addComment(climb.getClimbId(), request);
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
        newComment.setClimbId(climb.getClimbId());
        newComment.setComment(comment);
        newComment.setDate(request.getDate());
        newComment.setUserName(mainUser.getUserName());

        comments.add(0, newComment);
        adapter.notifyDataSetChanged();
        noCommentsText.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to get the User Rating from the database and populate the Your Rating Text View.
     *
     * @param climbId  - the climb that is selected
     * @param userName - the user for the query
     */
    public void getYourRating(int climbId, String userName) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Call<Integer> yourRatingCall = ApiManager.getClamberService().getRatingForClimbByUser(climbId, userName);
            yourRatingCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {

                    if (response.code() == 200) {
                        yourRatingInt = response.body();
                        yourRatingTextView.setVisibility(View.VISIBLE);
                        if (yourRatingInt == NO_DATA) {
                            yourRatingTextView.setText(getContext().getString(R.string.your_rating_s, ""));
                        } else {
                            yourRatingTextView.setText(getContext().getString(R.string.your_rating_s, yourRatingInt));
                        }
                    } else {
                        Log.d(TAG, "Non 200 response returned - check server");
                    }

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d(TAG, "Failure attempting to get user rating");
                }
            });

        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Method to get the average of all of the user ratings on a climb and populate the User Rating
     * Text View
     *
     * @param climbId - the cilmb that is selected.
     */
    public void getAvgUserRatingForClimb(int climbId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Call<Double> avgRatingCall = ApiManager.getClamberService().getUserAverageRatingForClimb(climbId);
            avgRatingCall.enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (response.code() == 200) {
                        userAvgRating = response.body();
                        userRatingTextView.setVisibility(View.VISIBLE);
                        if (userAvgRating == NO_DATA) {
                            userRatingTextView.setText(getContext().getString(R.string.user_rating_s, ""));
                        } else {
                            userRatingTextView.setText(getContext().getString(R.string.user_rating_s, userAvgRating));
                        }
                    } else {
                        Log.d(TAG, "Non 200 response code returned");
                    }
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    Log.d(TAG, "Failure when attempting to get average ratings");
                }
            });

        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to add a rating to the database for a user based off of up and down arrow button clicks.
     * @param request - request contains the climb id, user name and rating. Information is populated
     *                in the onClick methods for the up and down arrows.
     */
    public void addRatingForUser(UserRatingRequest request){
        final Call<Integer> addRatingCall = ApiManager.getClamberService().addUserRating(request);
        addRatingCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 200) {
                    yourRatingTextView.setText(getContext().getString(R.string.your_rating_s, response.body()));
                    getAvgUserRatingForClimb(climb.getClimbId());
                    userRatingTextView.setText(getContext().getString(R.string.user_rating_s, userAvgRating));
                }
                else {
                    Log.d(TAG, "Non 200 response code returned - check server");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "Failed to add rating for user");
            }
        });
    }


    /**
     * When the Up Arrow Button is selected, the add rating method is called and given the
     * climb id, user name and the existing rating plus one.
     */
    @OnClick(R.id.comments_fragment_up_arrow)
    public void onUpArrowClicked(){
        UserRatingRequest request = new UserRatingRequest();
        request.setUsername(mainUser.getUserName());
        request.setClimbId(climb.getClimbId());
        if (yourRatingInt != NO_DATA ){
            yourRatingInt = yourRatingInt +1;
        }
        else {
            yourRatingInt = climb.getGymRating() +1;
        }
        request.setRating(yourRatingInt);
        addRatingForUser(request);

    }

    /**
     * When the down arrow is selected, the add rating method is called and given the climb id,
     * user name and the existing rating minus one.
     */
    @OnClick(R.id.comments_fragment_down_arrow)
    public void onDownArrowClicked(){
        UserRatingRequest request = new UserRatingRequest();
        request.setUsername(mainUser.getUserName());
        request.setClimbId(climb.getClimbId());
        if (yourRatingInt != NO_DATA) {
            yourRatingInt = yourRatingInt -1;
        }
        else {
            yourRatingInt = climb.getGymRating() -1;

        }
        request.setRating(yourRatingInt);
        addRatingForUser(request);

    }
}