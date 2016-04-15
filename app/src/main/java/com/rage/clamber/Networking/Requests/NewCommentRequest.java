package com.rage.clamber.Networking.Requests;

/**
 * Comment body for server posts
 */
public class NewCommentRequest {
    protected String username;
    protected int climbId;
    protected String commmentText;

    //Date is stored as a milliseconds in the database. When displayed, it is converted to a
    //DateTime object using the Joda library.
    protected long date;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
