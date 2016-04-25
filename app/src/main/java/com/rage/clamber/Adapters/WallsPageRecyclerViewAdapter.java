package com.rage.clamber.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rage.clamber.Data.WallSection;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to set up each section of the GridLayout used to display each wall.
 */
public class WallsPageRecyclerViewAdapter extends RecyclerView.Adapter<WallsPageRecyclerViewAdapter.WallsViewHolder>{

    protected List<WallSection> wallSections;
    protected Context context;


    public WallsPageRecyclerViewAdapter(List<WallSection> wallSectionArrayList, OnWallSelectedListener wallSelectedListener, Context context) {
        wallSections = wallSectionArrayList;
        listener = wallSelectedListener;
        this.context = context;
    }

    private final OnWallSelectedListener listener;

    public interface OnWallSelectedListener {
        void onWallSelected(WallSection wallSection);
    }


    @Override
    public WallsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View wallsGrid = inflater.inflate(R.layout.wall_grid_view, parent, false);

        return new WallsViewHolder(wallsGrid);
    }

    @Override
    public void onBindViewHolder(WallsViewHolder holder, int position) {
        final WallSection oneWall = wallSections.get(position);
        String imageURL = ApiManager.getImageUrl("assets/wall_sections/" + oneWall.getId() + ".jpg");
        Picasso.with(context).load(imageURL).fit().centerCrop().into(holder.wallImageView);
//        holder.wallTextView.setText(oneWall.getName());
        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (listener != null) {
                 listener.onWallSelected(oneWall);
             }
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallSections.size();
    }

    public static class WallsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.wall_grid_image_view)
        ImageView wallImageView;
        View fullView;
        public WallsViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
