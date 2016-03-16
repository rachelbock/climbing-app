package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rage.clamber.Adapters.ClimbsRecyclerViewAdapter;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment to display the climbs row recycler view.
 */
public class ClimbsFragment extends Fragment {
    @Bind(R.id.climbs_fragment_recycler_view)
    RecyclerView recyclerView;

    public static final String [] CLIMB_NUMS = {"1", "2", "3", "4", "5"};

    public ClimbsFragment() {
        // Required empty public constructor
    }

    public static ClimbsFragment newInstance() {
        ClimbsFragment fragment = new ClimbsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_climbs, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ClimbsRecyclerViewAdapter adapter = new ClimbsRecyclerViewAdapter(CLIMB_NUMS);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

}
