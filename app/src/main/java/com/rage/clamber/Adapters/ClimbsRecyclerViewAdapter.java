package com.rage.clamber.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rage.clamber.Data.Climb;
import com.rage.clamber.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display the rows of climbs.
 */
public class ClimbsRecyclerViewAdapter extends RecyclerView.Adapter<ClimbsRecyclerViewAdapter.ClimbsViewHolder> {


    protected ArrayList<Climb> climbs;

    public ClimbsRecyclerViewAdapter(ArrayList<Climb> climbArrayList) {
        climbs = climbArrayList;
    }

    @Override
    public ClimbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View climbsView = inflater.inflate(R.layout.climb_row, parent, false);
        return new ClimbsViewHolder(climbsView);
    }

    @Override
    public void onBindViewHolder(ClimbsViewHolder holder, int position) {
        Climb climb = climbs.get(position);
        holder.gradeDataTextView.setText(Integer.toString(climb.getGymRating()));
        holder.projectCheckBox.setChecked(climb.isProject());
        holder.completedCheckBox.setChecked(climb.isCompleted());
        holder.styleDataTextView.setText(climb.getType());
        holder.routeColorTextView.setText(climb.getTape_color());

    }

    @Override
    public int getItemCount() {
        return climbs.size();
    }

    public static class ClimbsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.climb_row_comments_button)
        Button commentsButton;

        @Bind(R.id.climb_row_completed_checkbox)
        CheckBox completedCheckBox;
        @Bind(R.id.climb_row_completed_text)
        TextView completedTextView;

        @Bind(R.id.climb_row_grade_data)
        TextView gradeDataTextView;
        @Bind(R.id.climb_row_grade_text)
        TextView gradeTextView;

        @Bind(R.id.climb_row_route_color_text)
        TextView routeColorTextView;
        @Bind(R.id.climb_row_route_color_image)
        ImageView routeColorImage;

        @Bind(R.id.climb_row_project_text)
        TextView projectTextView;
        @Bind(R.id.climb_row_project_checkbox)
        CheckBox projectCheckBox;

        @Bind(R.id.climb_row_style_data)
        TextView styleDataTextView;
        @Bind(R.id.climb_row_style_text)
        TextView styleTextView;

        public ClimbsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
