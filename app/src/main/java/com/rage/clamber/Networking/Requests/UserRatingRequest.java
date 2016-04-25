package com.rage.clamber.Networking.Requests;

/**
 * Stores the data needed to update the database with a user's rating for a particular climb.
 */
public class UserRatingRequest {

    protected String username;
    protected int climbId;
    protected int rating;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
