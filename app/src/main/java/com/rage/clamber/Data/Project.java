package com.rage.clamber.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to hold project data
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private int projectId;
    private int climbId;
    private String userName;

    public Project() {
        //no-arg constructor for jackson
    }

    public Project(int climbIdNum, String user) {
        climbId = climbIdNum;
        userName = user;
    }

    public Project(int projectIdNum, int climbIdNum, String user) {
        projectId = projectIdNum;
        climbId = climbIdNum;
        userName = user;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getClimbId() {
        return climbId;
    }

    public void setClimbId(int climbId) {
        this.climbId = climbId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
