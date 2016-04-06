package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Displays user information and recommendations.
 */
public class UserInfoFragment extends Fragment {

    public static final String [] CLIMB_NUMS = {"1", "2", "3", "4", "5"};

    @Bind(R.id.user_activity_recycler_view)
    public RecyclerView recyclerView;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, rootView);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ClimbsRecyclerViewAdapter adapter = new ClimbsRecyclerViewAdapter(CLIMB_NUMS);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }


    @OnClick(R.id.user_activity_user_info_button)
    public void onClickButtonClicked(Button button) {
        UserInfoDialogFragment fragment = new UserInfoDialogFragment();
        fragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }
}
