package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

        //Sets the images for the image buttons.
        //TODO: Remove Uri from first image - this is here as a reminder
//        Uri.parse("http://10.0.2.2/8080/assets/walls/1.jpg")
        Picasso.with(getActivity()).load(R.drawable.wall_1).fit().centerCrop().into(wall1Button);
        Picasso.with(getActivity()).load(R.drawable.wall_2).fit().centerCrop().into(wall2Button);
        Picasso.with(getActivity()).load(R.drawable.wall_3).fit().centerCrop().into(wall3Button);
        Picasso.with(getActivity()).load(R.drawable.wall_4).fit().centerCrop().into(wall4Button);
        return rootView;
    }

    /**
     * OnClick listeners for each of the walls. When selected, the logged in user and wall number
     * are passed through to the WallSectionFragment to display the appropriate WallSection list.
     */
    @OnClick({R.id.wall_page_wall_1_button, R.id.wall_page_wall_2_button, R.id.wall_page_wall_3_button, R.id.wall_page_wall_4_button})
    public void onWall1ButtonClicked(ImageButton button) {
        int wallNum = Integer.parseInt(button.getTag().toString());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, WallSectionFragment.newInstance(mainUser, wallNum));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
