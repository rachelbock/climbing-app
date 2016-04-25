package com.rage.clamber.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Adapters.WallsPageRecyclerViewAdapter;
import com.rage.clamber.Data.User;
import com.rage.clamber.Data.WallSection;
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
 * Fragment called from WallFragment to display the individual wall sections on the selected
 * main Wall.
 */
public class WallSectionFragment extends Fragment implements WallsPageRecyclerViewAdapter.OnWallSelectedListener {

    public static final String TAG = WallSectionFragment.class.getSimpleName();
    public static final String ARG_WALL_SECTION = "Wall Section Id";
    protected User mainUser;
    protected List<WallSection> wallSections;
    protected int wall;
    protected WallsPageRecyclerViewAdapter adapter;


    @Bind(R.id.walls_page_grid_recycler_view)
    RecyclerView recyclerView;

    public WallSectionFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a WallSectionFragment - called from teh Walls Fragment.
     * @param user - the logged in user.
     * @param wallId - the main wall that is currently selected
     * @return - new WallSectionFragment
     */
    public static WallSectionFragment newInstance(User user, int wallId) {
        WallSectionFragment fragment = new WallSectionFragment();
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        args.putInt(WallsFragment.ARG_WALL_ID, wallId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall_section, container, false);
        ButterKnife.bind(this, rootView);
        wallSections = new ArrayList<>();
        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        wall = getArguments().getInt(WallsFragment.ARG_WALL_ID);
        getWallSectionsByWall(mainUser.getUserName(), wall);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new WallsPageRecyclerViewAdapter(wallSections, WallSectionFragment.this, getActivity());
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    /**
     * Method to make a network call to the Clamber Server to get all WallSections for the specified
     * Wall. It updates the WallsPageRecyclerViewAdapter to display the returned WallSection data.
     * @param userName - userName is needed for the path parameters.
     * @param wallId - wallId tells the query which wall to pull sections from.
     */
    public void getWallSectionsByWall(String userName, int wallId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            final Call<List<WallSection>> wallSectionCall = ApiManager.getClamberService().getWallSectionByWall(userName, wallId);

            wallSectionCall.enqueue(new Callback<List<WallSection>>() {
                @Override
                public void onResponse(Call<List<WallSection>> call, Response<List<WallSection>> response) {

                    if (response.code() == 200){
                     List<WallSection> wallSectionsArrayList = response.body();
                            wallSections.addAll(wallSectionsArrayList);
                            adapter.notifyDataSetChanged();
                    }
                    else {
                        Log.d(TAG, "Non 200 response received - check server");
                    }



                }

                @Override
                public void onFailure(Call<List<WallSection>> call, Throwable t) {
                    Log.d(TAG, "Failure on retrieving wall sections by wall id ");
                }
            });
        }
    }

    /**
     * Listener on the adapter to return which wallSection was selected. Launches the Climbs Fragment.
     * @param wallSection - the wallsection that the user selected.
     */
    @Override
    public void onWallSelected(WallSection wallSection) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, ClimbsFragment.newInstance(mainUser, wall, wallSection.getId()));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
