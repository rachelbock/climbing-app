package com.rage.clamber.Fragments.HomeActivity.UserInfo;


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
import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.User;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.Networking.Requests.NewUserDataRequest;
import com.rage.clamber.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Displays user information and recommendations.
 */
public class UserInfoFragment extends Fragment {

    public static final String TAG = UserInfoFragment.class.getSimpleName();
    protected User mainUser;
    protected List<Climb> climbList;
    protected int layoutId;
    protected ClimbsRecyclerViewAdapter adapter;

    @Bind(R.id.user_activity_recycler_view)
    public RecyclerView recyclerView;
    @Bind(R.id.user_info_fragment_no_history_text)
    public TextView noHistoryText;
    @Bind(R.id.user_info_fragment_user_name_text_view)
    public TextView userNameTextView;
    @Bind(R.id.user_info_fragment_height_text_view)
    public TextView heightTextView;
    @Bind(R.id.user_info_fragment_skill_level_text_view)
    public TextView skillLevelTextView;


    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the UserInfoFragment - called when the UserInfo Button is clicked.
     *
     * @param user - logged in user
     * @return - new UserInfoFragment
     */
    public static UserInfoFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, rootView);

        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        if (mainUser != null) {
            userNameTextView.setText(mainUser.getUserName());
            heightTextView.setText(Integer.toString(mainUser.getHeight() / 12) + "' " + Integer.toString(mainUser.getHeight() % 12) + "''");

            if (mainUser.getSkillLevel() == -1) {
                skillLevelTextView.setText("B");
            } else {
                skillLevelTextView.setText(Integer.toString(mainUser.getSkillLevel()));
            }
        }
        climbList = new ArrayList<>();
        layoutId = R.id.home_page_frame_layout;
        getCompletedClimbsForUser(mainUser.getUserName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClimbsRecyclerViewAdapter(climbList, mainUser, getActivity(), layoutId);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    /**
     * Method to get all of the climbs that have been marked as completed by the user.
     *
     * @param username - username for the query.
     */
    public void getCompletedClimbsForUser(String username) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Call<List<Climb>> completedCall = ApiManager.getClamberService().getCompletedForUser(username);
            completedCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    if (response.code() == 200) {
                        List<Climb> climbs = response.body();
                        climbList.addAll(climbs);

                    } else {
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Climb>> call, Throwable t) {
                    Log.d(TAG, "Failure when attempting to return projects", t);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * When the History button is clicked, set the completed climbs to visible.
     * If there are no completed climbs then a message displays telling the user to mark climbs
     * as completed.
     */
    @OnClick(R.id.user_fragment_history_button)
    public void onHistoryButtonClicked() {
        recyclerView.setVisibility(View.VISIBLE);
        if (climbList.isEmpty()) {
            noHistoryText.setVisibility(View.VISIBLE);
        }
    }


    /**
     * OnClick method to launch the update user dialog fragment.
     */
    @OnClick(R.id.user_info_fragment_about_me_button)
    public void onUpdateInfoButtonClicked() {
        UpdateUserDialogFragment dialogFragment = UpdateUserDialogFragment.newInstance(mainUser);
        //Sets this fragment as the target fragment for the dialogfragment
        dialogFragment.setTargetFragment(this, 0);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    /**
     * Method called from UpdateUserDialogFragment to send a post to update the existing user info.
     *
     * @param user
     */
    public void onUserUpdate(User user) {
        final NewUserDataRequest request = new NewUserDataRequest();
        request.setUsername(user.getUserName());
        request.setHeight(user.getHeight());
        request.setSkill(user.getSkillLevel());

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Call<User> updateUserCall = ApiManager.getClamberService().updateExistingUser(user.getUserName(), request);
            updateUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d(TAG, "successfully updated user");
                    User updatedUser = response.body();
                    mainUser.setHeight(updatedUser.getHeight());
                    mainUser.setSkillLevel(updatedUser.getSkillLevel());
                    heightTextView.setText(Integer.toString(mainUser.getHeight() / 12) + "' " + Integer.toString(mainUser.getHeight() % 12) + "''");
                    if (mainUser.getSkillLevel() == -1) {
                        skillLevelTextView.setText("B");
                    } else {
                        skillLevelTextView.setText(Integer.toString(mainUser.getSkillLevel()));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "failed to update user", t);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }
}


