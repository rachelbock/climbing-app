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
import com.rage.clamber.Networking.ApiManager;
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

/**
 * Fragment to display climbs by WallSection
 */
public class ClimbsFragment extends Fragment {

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
            final Call<List<Climb>> climbsCall = ApiManager.getClamberService().getClimbsByWallSection(userName, wallIdNum, wallSectionId);
            climbsCall.enqueue(new Callback<List<Climb>>() {
                @Override
                public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                    if (response.code() == 200) {
                        List<Climb> climbs = response.body();
                        for (int i = 0; i < climbs.size(); i++) {
                            climbArrayList.add(climbs.get(i));
                        }

                        ClimbsRecyclerViewAdapter adapter = new ClimbsRecyclerViewAdapter(climbArrayList, mainUser);
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
}
