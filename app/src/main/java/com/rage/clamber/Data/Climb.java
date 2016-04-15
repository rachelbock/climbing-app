package com.rage.clamber.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Displays climbs in database
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Climb {

    private int climbId;
    private int gymRating;
    private int userRating;
    private String tapeColor;
    private int wallId;
    private boolean isProject;
    private boolean isCompleted;
    private String type;

    public int getClimbId() {
        return climbId;
    }

    public void setClimbId(int climbId) {
        this.climbId = climbId;
    }

    public int getGymRating() {
        return gymRating;
    }

    public void setGymRating(int gymRating) {
        this.gymRating = gymRating;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public String getTapeColor() {
        return tapeColor;
    }

    public void setTapeColor(String tapeColor) {
        this.tapeColor = tapeColor;
    }

    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    public boolean isProject() {
        return isProject;
    }

    public void setProject(boolean isProject) {
        this.isProject = isProject;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
