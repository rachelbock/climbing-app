package com.rage.clamber.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rage.clamber.Data.Comment;
import com.rage.clamber.Data.User;
import com.rage.clamber.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display comment rows
 */
public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.CommentsViewHolder> {

    public static final String TAG = CommentsRecyclerViewAdapter.class.getSimpleName();
    protected User mainUser;
    protected List<Comment> comments;
    protected Context context;

    public CommentsRecyclerViewAdapter(List<Comment> commentsList, User user, Context context){
        comments = commentsList;
        mainUser = user;
        this.context = context;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View commentsView = inflater.inflate(R.layout.comment_row, parent, false);
        return new CommentsViewHolder(commentsView);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        Comment oneComment = comments.get(position);
        holder.commentsTextView.setText(oneComment.getComment());
        long raw = oneComment.getDate();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(raw);

        String date = "";
        date += gregorianCalendar.get(Calendar.YEAR);
        date += " " + gregorianCalendar.get(Calendar.HOUR);
        date += ":" + gregorianCalendar.get(Calendar.MINUTE);
//        String date = DateUtils.formatDateTime(context, raw, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR);
        holder.dateTextView.setText(date);

        holder.usernameTextView.setText(oneComment.getUserName());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.comments_row_username_text_view)
        TextView usernameTextView;
        @Bind(R.id.comments_row_comments_text_view)
        TextView commentsTextView;
        @Bind(R.id.comments_row_date_text_view)
        TextView dateTextView;

        View fullView;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
