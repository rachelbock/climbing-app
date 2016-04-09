package com.rage.clamber.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Adapters.ClimbsRecyclerViewAdapter;
import com.rage.clamber.AsyncTasks.ClamberService;
import com.rage.clamber.AsyncTasks.Requests.UserClimbDataRequest;
import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.Project;
import com.rage.clamber.Data.User;
import com.rage.clamber.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Fragment to display climbs by WallSection
 */
public class ClimbsFragment extends Fragment implements ClimbsRecyclerViewAdapter.onProjectCheckBoxClickedListener, ClimbsRecyclerViewAdapter.onCompletedCheckBoxClickedListener {

    public static final String TAG = ClimbsFragment.class.getSimpleName();
    protected User mainUser;
    protected List<Climb> climbArrayList;
    protected int wallSectionIdNum;
    protected int wallIdNum;

    @Bind(R.id.climbs_fragment_recycler_view)
    RecyclerView recyclerView;


    public ClimbsFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a ClimbsFragment. Launched from the WallSectionFragment.
     *
     * @param user          - the logged in user.
     * @param wallId        - the main wall that is currently selected
     * @param wallSectionId - the specific wallSection that the climbs are on
     * @return - new climb fragment
     */
    public static ClimbsFragment newInstance(User user, int wallId, int wallSectionId) {
        ClimbsFragment fragment = new ClimbsFragment();
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        args.putInt(WallsFragment.ARG_WALL_ID, wallId);
        args.putInt(WallSectionFragment.ARG_WALL_SECTION, wallSectionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_climbs, container, false);
        ButterKnife.bind(this, rootView);

        climbArrayList = new ArrayList<>();
        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        wallSectionIdNum = getArguments().getInt(WallSectionFragment.ARG_WALL_SECTION);
        wallIdNum = getArguments().getInt(WallsFragment.ARG_WALL_ID);
        getClimbsByWallSection(mainUser.getUserName(), wallSectionIdNum);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    /**
     * Method to make a network call to the Clamber Server to get all Climbs for the specified wall
     * section. It also updates the adapter to pass through the returned list of climbs.
     *
     * @param userName      - userName is used in the database query to return project and completed statuses
     * @param wallSectionId - wallSectionId tells the query what climbs to return.
     */
    public void getClimbsByWallSection(String userName, int wallSectionId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomePage.CONNECTION_WEB_ADDRESS)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            ClamberService clamber = retrofit.create(ClamberService.class);
            final Call<List<Climb>> climbsCall = clamber.getClimbsByWallSection(userName, wallIdNum, wallSectionId);
            climbsCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    if (response.code() == 200) {
                        List<Climb> climbs = response.body();
                        for (int i = 0; i < climbs.size(); i++) {
                            climbArrayList.add(climbs.get(i));
                        }

                        ClimbsRecyclerViewAdapter adapter = new ClimbsRecyclerViewAdapter(climbArrayList, ClimbsFragment.this, ClimbsFragment.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    } else {
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }


                }

                @Override
                public void onFailure(Call<List<Climb>> call, Throwable t) {
                    Log.d(TAG, "Failure while attempting to return climbs by wall section", t);

                }
            });

        }
    }

    /**
     * Method called from the ClimbRecyclerViewAdapter when the Project Checkbox is clicked. It returns the
     * climb Id and boolean value for whether or not the box is checked. With that information, a network
     * call is made to either POST a new project to the database or DELETE an existing project for the user.
     * @param climbId - the id of the climb where the project checkbox was selected
     * @param isChecked - the boolean value of the project checkbox.
     */
    @Override
    public void onProjectCheckBoxClicked(int climbId, boolean isChecked) {
        UserClimbDataRequest request = new UserClimbDataRequest();
        request.setClimbId(climbId);
        request.setUsername(mainUser.getUserName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HomePage.CONNECTION_WEB_ADDRESS)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        ClamberService clamber = retrofit.create(ClamberService.class);
        Log.d(TAG, "The id is: " + climbId + " and isChecked is: " + isChecked);
        if (!isChecked) {

            final Call<Project> createProjectCall = clamber.createProject(request);
            createProjectCall.enqueue(new Callback<Project>() {
                @Override
                public void onResponse(Call<Project> call, Response<Project> response) {
                    Log.d(TAG, "Successfully set up project for user: " + mainUser.getUserName());
                }

                @Override
                public void onFailure(Call<Project> call, Throwable t) {
                    Log.d(TAG, "Failed to set up project for user:" + mainUser.getUserName(), t);
                }
            });
        } else {
            final Call<Boolean> removeProjectCall = clamber.removeProject(mainUser.getUserName(), climbId);
            removeProjectCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body()) {
                        Log.d(TAG, "Successfully removed project for user: " + mainUser.getUserName());
                    } else {
                        Log.d(TAG, "Failed to delete project for user: " + mainUser.getUserName());

                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.d(TAG, "Failed to delete project for user: " + mainUser.getUserName(), t);
                }
            });
        }

    }
    /**
     * Method called from the ClimbRecyclerViewAdapter when the Completed Checkbox is clicked. It returns the
     * climb Id and boolean value for whether or not the box is checked. With that information, a network
     * call is made to either POST a new completed climb to the database or DELETE an existing one for the user.
     * @param climbId - the id of the climb where the completed checkbox was selected
     * @param isChecked - the boolean value of the completed checkbox.
     */
    @Override
    public void onCompletedCheckBoxClicked(int climbId, boolean isChecked) {
        UserClimbDataRequest request = new UserClimbDataRequest();
        request.setClimbId(climbId);
        request.setUsername(mainUser.getUserName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HomePage.CONNECTION_WEB_ADDRESS)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        ClamberService clamber = retrofit.create(ClamberService.class);
        Log.d(TAG, "The id is: " + climbId + " and isChecked is: " + isChecked);
        if (!isChecked) {

            final Call<Boolean> createCompletedCall = clamber.createCompleted(request);
            createCompletedCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Log.d(TAG, "Successfully set up completed climb for user: " + mainUser.getUserName());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.d(TAG, "Failed to set up completed climb for user:" + mainUser.getUserName(), t);

                }

            });
        } else {
            final Call<Boolean> removeCompletedCall = clamber.removeCompleted(mainUser.getUserName(), climbId);
            removeCompletedCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body()) {
                        Log.d(TAG, "Successfully removed completed climb for user: " + mainUser.getUserName());
                    } else {
                        Log.d(TAG, "Failed to delete completed climb for user: " + mainUser.getUserName());

                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.d(TAG, "Failed to delete completed climb for user: " + mainUser.getUserName(), t);

                }
            });
        }
    }
}
