package com.rage.clamber.Networking.Requests;

/**
 * Class to hold the user name and climb id for project and completed climb server requests.
 */
public class UserClimbDataRequest {

    protected String username;
    protected int climbId;


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
}
