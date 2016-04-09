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
 * Displays users projects.
 */
public class ProjectsFragment extends Fragment implements ClimbsRecyclerViewAdapter.onProjectCheckBoxClickedListener, ClimbsRecyclerViewAdapter.onCompletedCheckBoxClickedListener {


    public static final String TAG = ProjectsFragment.class.getSimpleName();
    protected User mainUser;
    protected List<Climb> climbArrayList;

    @Bind(R.id.projects_fragment_recycler_view)
    RecyclerView recyclerView;

    public ProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the Projects Fragment. Called when the Projects Button is clicked on the Home Page.
     * @param user - logged in user
     * @return new Projects Fragment
     */
    public static ProjectsFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        ProjectsFragment fragment = new ProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, rootView);

        mainUser = getArguments().getParcelable(HomePage.ARG_USER);

        climbArrayList = new ArrayList<>();

        getProjectsforUser(mainUser.getUserName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Log.d(TAG, mainUser.getUserName());

        return rootView;
    }

    /**
     * Method to make a network call to the Clamber Server to get all Climbs that have been marked
     * as a project by the user. It updates the ClimbsRecyclerViewAdapter with the list of climbs
     * to display
     *
     * @param userName - userName is used in the database query to return the appropriate projects.
     */
    public void getProjectsforUser(String userName) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomePage.CONNECTION_WEB_ADDRESS)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            ClamberService clamber = retrofit.create(ClamberService.class);
            Call<List<Climb>> projectsCall = clamber.getProjectsForUser(userName);

            Log.d(TAG, userName);

            projectsCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    if (response.code() == 200) {
                        List<Climb> climbs = response.body();

                        for (int i = 0; i < climbs.size(); i++) {
                            climbArrayList.add(climbs.get(i));
                        }
                    } else {
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }

                    ClimbsRecyclerViewAdapter adapter = new ClimbsRecyclerViewAdapter(climbArrayList, ProjectsFragment.this, ProjectsFragment.this);
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onFailure(Call<List<Climb>> call, Throwable t) {
                    Log.d(TAG, "Failure when attempting to return projects for user", t);
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
        if (!isChecked){

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
        }
        else {
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

