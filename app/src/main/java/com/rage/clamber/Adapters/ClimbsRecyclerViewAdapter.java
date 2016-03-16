package com.rage.clamber.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display the rows of climbs.
 */
public class ClimbsRecyclerViewAdapter extends RecyclerView.Adapter<ClimbsRecyclerViewAdapter.ClimbsViewHolder> {

    //TODO: Set up a listener interface

    protected String [] climbGrades;

    public ClimbsRecyclerViewAdapter(String [] climbs) {
        climbGrades = climbs;
    }


    @Override
    public ClimbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View climbsView = inflater.inflate(R.layout.climb_row, parent, false);
        return new ClimbsViewHolder(climbsView);
    }

    @Override
    public void onBindViewHolder(ClimbsViewHolder holder, int position) {
        String oneClimb = climbGrades[position];
        holder.gradeDataTextView.setText(oneClimb);
    }

    @Override
    public int getItemCount() {
        return climbGrades.length;
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
