package com.rage.clamber.Networking.Requests;

/**
 * Comment body for server posts
 */
public class NewCommentRequest {
    protected String username;
    protected int climbId;
    protected String commmentText;
    protected long dateText;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getClimbId() {
        return climbId;
    }

    public void setClimbId(int climbId) {
        this.climbId = climbId;
    }

    public String getCommmentText() {
        return commmentText;
    }

    public void setCommmentText(String commmentText) {
        this.commmentText = commmentText;
    }

    public long getDateText() {
        return dateText;
    }

    public void setDateText(long dateText) {
        this.dateText = dateText;
    }
}
