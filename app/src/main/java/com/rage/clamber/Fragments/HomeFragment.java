package com.rage.clamber.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Data.User;
import com.rage.clamber.Data.WallSection;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Displays the home page.
 */
public class HomeFragment extends Fragment {

    public User mainUser;
    public static final String TAG = HomeFragment.class.getSimpleName();
    public List<WallSection> wallSections;
    @Bind(R.id.home_page_image_1_grid_view)
    ImageView wallImage1;
    @Bind(R.id.home_page_image_2_grid_view)
    ImageView wallImage2;
    @Bind(R.id.home_page_image_3_grid_view)
    ImageView wallImage3;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        wallSections = new ArrayList<>();
        mainUser = getArguments().getParcelable(HomePage.ARG_USER);
        getLastUpdatedWallSections(mainUser.getUserName());

        return rootView;
    }

    public void getLastUpdatedWallSections(String username){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            final Call<List<WallSection>> lastUpdateWallCall = ApiManager.getClamberService().getLastUpdatedWalls(mainUser.getUserName());
            lastUpdateWallCall.enqueue(new Callback<List<WallSection>>() {
                @Override
                public void onResponse(Call<List<WallSection>> call, Response<List<WallSection>> response) {
                    if (response.code() == 200) {
                        List<WallSection> lastUpdateWallSections = response.body();
                        wallSections.addAll(lastUpdateWallSections);

                        Picasso.with(getActivity()).load(ApiManager.getImageUrl("assets/wall_sections/" + wallSections.get(0).getId() + ".jpg")).fit().centerCrop().into(wallImage1);
                        Picasso.with(getActivity()).load(ApiManager.getImageUrl("assets/wall_sections/" + wallSections.get(1).getId() + ".jpg")).fit().centerCrop().into(wallImage2);
                        Picasso.with(getActivity()).load(ApiManager.getImageUrl("assets/wall_sections/" + wallSections.get(2).getId() + ".jpg")).fit().centerCrop().into(wallImage3);
                    }
                    else {
                        Log.d(TAG, "Non 200 response code returned - check server");
                    }
                }

                @Override
                public void onFailure(Call<List<WallSection>> call, Throwable t) {
                    Log.d(TAG, "Failed to get the last updated climbs");
                }
            });
        }
        else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.home_page_image_1_grid_view, R.id.home_page_image_2_grid_view, R.id.home_page_image_3_grid_view})
    public void onWallSectionClicked(ImageView sectionImage) {
        int wallNum = Integer.parseInt(sectionImage.getTag().toString());
        WallSection wallSection = wallSections.get(wallNum);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, ClimbsFragment.newInstance(mainUser, wallSection.getMainWallId(), wallSection.getId()));
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
