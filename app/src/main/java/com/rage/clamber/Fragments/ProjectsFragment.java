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

import com.rage.clamber.Adapters.ClimbsRecyclerViewAdapter;
import com.rage.clamber.AsyncTasks.ClamberService;
import com.rage.clamber.Data.Climb;
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
public class ProjectsFragment extends Fragment {


    public static final String TAG = ProjectsFragment.class.getSimpleName();
    public static final String ARG_PROJECTS_FRAGMENT = "Projects Fragment User";
    protected User mainUser;
    protected ArrayList<Climb> climbArrayList;

    @Bind(R.id.projects_fragment_recycler_view)
    RecyclerView recyclerView;

    public ProjectsFragment() {
        // Required empty public constructor
    }

    public static ProjectsFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROJECTS_FRAGMENT, user);
        ProjectsFragment fragment = new ProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, rootView);

        mainUser = getArguments().getParcelable(ARG_PROJECTS_FRAGMENT);

        climbArrayList = new ArrayList<>();

        getProjectsforUser(mainUser.getUserName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Log.d(TAG, mainUser.getUserName());

        return rootView;
    }

    public void getProjectsforUser(String userName) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.103:8080/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            ClamberService clamber = retrofit.create(ClamberService.class);
            Call<List<Climb>> projectsCall = clamber.getProjectsForUser(userName);

            Log.d(TAG, userName);

            projectsCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    Log.d(TAG, "got response: " + response.code());
                    if (response.code() == 200) {
                       List<Climb> climbs = response.body();

                       for (int i = 0; i < climbs.size(); i++) {
                           Log.d(TAG, "adding result");
                           climbArrayList.add(climbs.get(i));
                        }
                    }

                    ClimbsRecyclerViewAdapter adapter = new ClimbsRecyclerViewAdapter(climbArrayList);
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<List<Climb>> call, Throwable t) {
                    Log.d(TAG, "Failure: ", t);
                }
            });

        }
    }
}
