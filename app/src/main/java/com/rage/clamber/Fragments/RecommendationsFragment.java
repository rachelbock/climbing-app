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
import android.widget.Toast;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Adapters.ClimbsRecyclerViewAdapter;
import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.User;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment to display recommended climbs for user. Called from ProjectsFragment.
 */
public class RecommendationsFragment extends Fragment {

    public static final String TAG = RecommendationsFragment.class.getSimpleName();
    protected User mainUser;
    protected List<Climb> climbArrayList;
    protected int layoutId;
    protected ClimbsRecyclerViewAdapter adapter;

    @Bind(R.id.recommendations_fragment_recycler_view)
    RecyclerView recyclerView;

    public RecommendationsFragment() {
        // Required empty public constructor
    }

    public static RecommendationsFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        RecommendationsFragment fragment = new RecommendationsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recommendations, container, false);
        ButterKnife.bind(this, rootView);

        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        climbArrayList = new ArrayList<>();
        layoutId = R.id.home_page_frame_layout;

        getRecommendationsForUser(mainUser.getUserName());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClimbsRecyclerViewAdapter(climbArrayList, mainUser, getActivity(), layoutId);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Method to get all climbs within plus or minus one of the user's defined skill level. Called
     * from the Projects Fragment.
     * @param username - username in the query.
     */
    public void getRecommendationsForUser(String username){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){

            Call<List<Climb>> recommendationsCall = ApiManager.getClamberService().getRecommendations(username);
            recommendationsCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    if (response.code() == 200){
                        List<Climb> climbs = response.body();
                        climbArrayList.addAll(climbs);
                    }
                    else{
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Climb>> call, Throwable t) {
                    Log.d(TAG, "Failure when attempting to return recommendations", t);

                }
            });

        }
        else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

}
