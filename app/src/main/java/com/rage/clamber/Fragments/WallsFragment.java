package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Data.User;
import com.rage.clamber.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class WallsFragment extends Fragment {

    public static final String ARG_WALL_ID = "Wall ID";
    protected User mainUser;
    @Bind(R.id.wall_page_wall_1_image)
    public ImageView wall1Image;
    @Bind(R.id.wall_page_wall_2_image)
    public ImageView wall2Image;
    @Bind(R.id.wall_page_wall_3_image)
    public ImageView wall3Image;
    @Bind(R.id.wall_page_wall_4_image)
    public ImageView wall4Image;

    public WallsFragment() {
        // Required empty public constructor
    }

    public static WallsFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(HomePage.ARG_USER, user);
        WallsFragment fragment = new WallsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_walls, container, false);
        ButterKnife.bind(this, rootView);
        mainUser = getArguments().getParcelable(HomePage.ARG_USER);

        Picasso.with(getActivity()).load(R.drawable.wall_1).fit().centerCrop().into(wall1Image);
        Picasso.with(getActivity()).load(R.drawable.wall_2).fit().centerCrop().into(wall2Image);
        Picasso.with(getActivity()).load(R.drawable.wall_3).fit().centerCrop().into(wall3Image);
        Picasso.with(getActivity()).load(R.drawable.wall_4).fit().centerCrop().into(wall4Image);
        return rootView;
    }

    /**
     * OnClick listeners for each of the walls. When selected, the logged in user and wall number
     * are passed through to the WallSectionFragment to display the appropriate WallSection list.
     */
    @OnClick({R.id.wall_page_wall_1_image, R.id.wall_page_wall_2_image, R.id.wall_page_wall_3_image, R.id.wall_page_wall_4_image})
    public void onWall1ButtonClicked(ImageView button) {
        int wallNum = Integer.parseInt(button.getTag().toString());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallSectionFragment.newInstance(mainUser, wallNum));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
