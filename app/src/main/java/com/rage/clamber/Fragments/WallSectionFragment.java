package com.rage.clamber.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rage.clamber.Adapters.WallsPageRecyclerViewAdapter;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment called from WallActivity to display the individual wall sections on the selected
 * main Wall.
 */
public class WallSectionFragment extends Fragment {

    public static final String[] WALL_NAMES = {"Wall 1", "Wall 2", "Wall 3", "Wall 4", "Wall 5","Wall 6","Wall 7","Wall 8","Wall 9", "Wall 10"};

    @Bind(R.id.walls_page_grid_recycler_view)
    RecyclerView recyclerView;

    public WallSectionFragment() {
        // Required empty public constructor
    }

    public static WallSectionFragment newInstance() {
        WallSectionFragment fragment = new WallSectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall_section, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        WallsPageRecyclerViewAdapter adapter = new WallsPageRecyclerViewAdapter(WALL_NAMES);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    //TODO: When the wall section is clicked - launch the Climbs Fragment.

}
