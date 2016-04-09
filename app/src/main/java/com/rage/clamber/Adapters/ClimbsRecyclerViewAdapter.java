package com.rage.clamber.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rage.clamber.Data.Climb;
import com.rage.clamber.Fragments.ProjectsFragment;
import com.rage.clamber.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display the rows of climbs.
 */
public class ClimbsRecyclerViewAdapter extends RecyclerView.Adapter<ClimbsRecyclerViewAdapter.ClimbsViewHolder> {


    protected List<Climb> climbs;

    public ClimbsRecyclerViewAdapter(List<Climb> climbArrayList, onProjectCheckBoxClickedListener ProjectcheckBoxlistener, onCompletedCheckBoxClickedListener CompletedCheckBoxListener) {
        climbs = climbArrayList;
        projectlistener = ProjectcheckBoxlistener;
        completedListener = CompletedCheckBoxListener;
    }

    private final onProjectCheckBoxClickedListener projectlistener;
    private final onCompletedCheckBoxClickedListener completedListener;

    public interface onProjectCheckBoxClickedListener {
        public void onProjectCheckBoxClicked(int climbId,  boolean isChecked);
    }

    public interface onCompletedCheckBoxClickedListener{
        public void onCompletedCheckBoxClicked(int climbId, boolean isChecked);
    }

    @Override
    public ClimbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View climbsView = inflater.inflate(R.layout.climb_row, parent, false);
        return new ClimbsViewHolder(climbsView);
    }

    @Override
    public void onBindViewHolder(final ClimbsViewHolder holder, int position) {
        Climb climb = climbs.get(position);
        Log.d(ProjectsFragment.TAG, "Climb id: " + climb.getClimbId());
        holder.gradeDataTextView.setText(Integer.toString(climb.getGymRating()));
        holder.projectCheckBox.setChecked(climb.isProject());
        holder.completedCheckBox.setChecked(climb.isCompleted());
        holder.styleDataTextView.setText(climb.getType());
        holder.routeColorTextView.setText(climb.getTape_color());

        holder.projectCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectlistener != null){
                    Climb oneClimb = climbs.get(holder.getAdapterPosition());
                    projectlistener.onProjectCheckBoxClicked(oneClimb.getClimbId(), oneClimb.isProject());
                    oneClimb.setProject(!oneClimb.isProject());
                }
            }
        });

        holder.completedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (completedListener != null){
                    Climb oneClimb = climbs.get(holder.getAdapterPosition());
                    completedListener.onCompletedCheckBoxClicked(oneClimb.getClimbId(), oneClimb.isCompleted());
                    oneClimb.setCompleted(!oneClimb.isCompleted());
                }
            }
        });
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

        View fullView;

        public ClimbsViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;

            ButterKnife.bind(this, itemView);

        }
    }


}
