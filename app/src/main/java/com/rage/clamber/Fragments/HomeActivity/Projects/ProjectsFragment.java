package com.rage.clamber.Fragments.HomeActivity.Projects;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.User;
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
 * Displays users projects.
 */
public class ProjectsFragment extends Fragment {


    public static final String TAG = ProjectsFragment.class.getSimpleName();
    protected User mainUser;
    protected List<Climb> climbArrayList;
    protected int layoutId;
    protected ClimbsRecyclerViewAdapter adapter;

    @Bind(R.id.projects_fragment_recycler_view)
    protected RecyclerView recyclerView;
    @Bind(R.id.projects_fragment_no_projects_text)
    protected TextView noProjectsText;

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

        layoutId = R.id.home_page_frame_layout;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClimbsRecyclerViewAdapter(climbArrayList, mainUser, getActivity(), layoutId);
        recyclerView.setAdapter(adapter);


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

            Call<List<Climb>> projectsCall = ApiManager.getClamberService().getProjectsForUser(userName);

            projectsCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    if (response.code() == 200) {
                        List<Climb> climbs = response.body();
                        climbArrayList.addAll(climbs);
                        if (climbArrayList.isEmpty()){
                            noProjectsText.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }

                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<Climb>> call, Throwable t) {
                    Log.d(TAG, "Failure when attempting to return projects for user", t);
                }
            });

        }
        else {
            Toast.makeText (getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * OnClick method to start up the RecommendationsFragment when the button is clicked.
     */
    @OnClick(R.id.projects_fragment_recommendations_fragment)
    public void onRecommendationsButtonClicked(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, RecommendationsFragment.newInstance(mainUser));
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

