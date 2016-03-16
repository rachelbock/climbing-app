package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rage.clamber.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class WallsFragment extends Fragment {

    @Bind(R.id.wall_page_wall_1_button)
    public ImageButton wall1Button;
    @Bind(R.id.wall_page_wall_2_button)
    public ImageButton wall2Button;
    @Bind(R.id.wall_page_wall_3_button)
    public ImageButton wall3Button;
    @Bind(R.id.wall_page_wall_4_button)
    public ImageButton wall4Button;

    public WallsFragment() {
        // Required empty public constructor
    }

    public static WallsFragment newInstance() {
        return new WallsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_walls, container, false);
        ButterKnife.bind(this, rootView);

        //Sets the images for the image buttons.
        Picasso.with(getActivity()).load(R.drawable.wall_1).fit().centerCrop().into(wall1Button);
        Picasso.with(getActivity()).load(R.drawable.wall_2).fit().centerCrop().into(wall2Button);
        Picasso.with(getActivity()).load(R.drawable.wall_3).fit().centerCrop().into(wall3Button);
        Picasso.with(getActivity()).load(R.drawable.wall_4).fit().centerCrop().into(wall4Button);
        return rootView;
    }

    @OnClick(R.id.wall_page_wall_1_button)
    public void onWall1ButtonClicked(ImageButton button) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        //TODO: Pass in wall 1 wall sections
    }
    @OnClick(R.id.wall_page_wall_2_button)
    public void onWall2ButtonClicked(ImageButton button) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        //TODO: Pass in wall 2 wall sections
    }
    @OnClick(R.id.wall_page_wall_3_button)
    public void onWall3ButtonClicked(ImageButton button) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        //TODO: Pass in wall 3 wall sections
    }
    @OnClick(R.id.wall_page_wall_4_button)
    public void onWall4ButtonClicked(ImageButton button){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        //Todo: Pass in wall 4 wall sections.
    }


}
