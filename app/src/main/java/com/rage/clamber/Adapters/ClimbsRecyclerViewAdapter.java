package com.rage.clamber.Adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.Project;
import com.rage.clamber.Data.User;
import com.rage.clamber.Fragments.HomeActivity.Walls.ClimbDetailFragment;
import com.rage.clamber.Networking.ApiManager;
import com.rage.clamber.Networking.Requests.UserClimbDataRequest;
import com.rage.clamber.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adapter to display the rows of climbs.
 */
public class ClimbsRecyclerViewAdapter extends RecyclerView.Adapter<ClimbsRecyclerViewAdapter.ClimbsViewHolder> {


    public static final String TAG = ClimbsRecyclerViewAdapter.class.getSimpleName();
    public static final String ARG_CLIMB = "Climb";
    protected List<Climb> climbs;
    protected User mainUser;
    protected FragmentActivity fragmentActivity;
    protected int layoutId;

    /**
     * Constructor for adapter
     * @param climbArrayList - list of climbs provided by the class that calls the adapter
     * @param user - the logged in user
     */
    public ClimbsRecyclerViewAdapter(List<Climb> climbArrayList, User user, FragmentActivity fragActivity, int id) {
        climbs = climbArrayList;
        mainUser = user;
        fragmentActivity = fragActivity;
        layoutId = id;
    }


    @Override
    public ClimbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View climbsView = inflater.inflate(R.layout.climb_row, parent, false);
        return new ClimbsViewHolder(climbsView);
    }

    /**
     * Sets the climbs contained in the list of climbs to their row.
     */
    @Override
    public void onBindViewHolder(final ClimbsViewHolder holder, int position) {
        Climb climb = climbs.get(position);
        if (climb.getGymRating() != -1){
            holder.gradeDataTextView.setText(Integer.toString(climb.getGymRating()));
        }
        else {
            holder.gradeDataTextView.setText("B");
        }
        holder.projectCheckBox.setChecked(climb.isProject());
        holder.completedCheckBox.setChecked(climb.isCompleted());
        holder.styleDataTextView.setText(climb.getType());
        Drawable background = holder.routeColorImage.getDrawable();
        ((GradientDrawable)background).setColor(Color.parseColor(climb.getTapeColor()));

        //OnClickListener for the ProjectCheckbox. When the checkbox is checked, a network call is made
        //to the Clamber Server to add that Project for the user. If the checkbox is unchecked, a call
        //is made to delete the project from the database. If either of those actions fail, a Log
        //message is printed.
        holder.projectCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Climb oneClimb = climbs.get(holder.getAdapterPosition());

                UserClimbDataRequest request = new UserClimbDataRequest();
                request.setClimbId(oneClimb.getClimbId());
                request.setUsername(mainUser.getUserName());
                DateTime now = DateTime.now(DateTimeZone.UTC);
                request.setDate(now.getMillis());

                if (!oneClimb.isProject()) {

                    final Call<Project> createProjectCall = ApiManager.getClamberService().createProject(request);
                    createProjectCall.enqueue(new Callback<Project>() {
                        @Override
                        public void onResponse(Call<Project> call, Response<Project> response) {
                            Log.d(TAG, "Successfully set up project for user: " + mainUser.getUserName());
                        }

                        @Override
                        public void onFailure(Call<Project> call, Throwable t) {
                            Log.d(TAG, "Failed to set up project for user:" + mainUser.getUserName(), t);
                        }
                    });
                } else {
                    final Call<Boolean> removeProjectCall = ApiManager.getClamberService().removeProject(mainUser.getUserName(), oneClimb.getClimbId());
                    removeProjectCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body()) {
                                Log.d(TAG, "Successfully removed project for user: " + mainUser.getUserName());
                            } else {
                                Log.d(TAG, "Failed to delete project for user: " + mainUser.getUserName());

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d(TAG, "Failed to delete project for user: " + mainUser.getUserName(), t);
                        }
                    });
                }
                oneClimb.setProject(!oneClimb.isProject());
            }
        });

        //OnClickListener for the CompletedCheckbox. When the checkbox is checked, a network call is made
        //to the Clamber Server to add the climb as a completed climb for the user. If the checkbox
        // is unchecked, a call is made to delete the completed climb from the database. If either
        // of those actions fail, a Log message is printed.
        holder.completedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Climb oneClimb = climbs.get(holder.getAdapterPosition());

                UserClimbDataRequest request = new UserClimbDataRequest();
                request.setClimbId(oneClimb.getClimbId());
                request.setUsername(mainUser.getUserName());
                DateTime now = DateTime.now(DateTimeZone.UTC);
                request.setDate(now.getMillis());

                if (!oneClimb.isCompleted()){

                    final Call<Boolean> createCompletedCall = ApiManager.getClamberService().createCompleted(request);
                    createCompletedCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Log.d(TAG, "Successfully set up completed climb for user: " + mainUser.getUserName());
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d(TAG, "Failed to set up completed climb for user:" + mainUser.getUserName(), t);

                        }

                    });
                }
                else {
                    final Call<Boolean> removeCompletedCall = ApiManager.getClamberService().removeCompleted(mainUser.getUserName(), oneClimb.getClimbId());
                    removeCompletedCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body()) {
                                Log.d(TAG, "Successfully removed completed climb for user: " + mainUser.getUserName());
                            } else {
                                Log.d(TAG, "Failed to delete completed climb for user: " + mainUser.getUserName());

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d(TAG, "Failed to delete completed climb for user: " + mainUser.getUserName(), t);

                        }
                    });
                }
                oneClimb.setCompleted(!oneClimb.isCompleted());
            }
        });

//            OnClickListener for the comments button. Launches comments tied to the climb in the
//            comments fragment.
            holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Climb oneClimb = climbs.get(holder.getAdapterPosition());
                    FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(layoutId, ClimbDetailFragment.newInstance(mainUser, oneClimb));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

    }

    /**
     * @return - returns the size of the list of climbs.
     */
    @Override
    public int getItemCount() {
        return climbs.size();
    }


    /**
     * ViewHolder class for the Climb Row.
     */
    public static class ClimbsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.climb_row_comments_button)
        ImageButton commentsButton;

        @Bind(R.id.climb_row_completed_checkbox)
        CheckBox completedCheckBox;

        @Bind(R.id.climb_row_grade_data)
        TextView gradeDataTextView;

        @Bind(R.id.climb_row_route_color_image)
        ImageView routeColorImage;

        @Bind(R.id.climb_row_project_checkbox)
        CheckBox projectCheckBox;

        @Bind(R.id.climb_row_style_data)
        TextView styleDataTextView;

        View fullView;

        public ClimbsViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;

            ButterKnife.bind(this, itemView);

        }

    }


}
