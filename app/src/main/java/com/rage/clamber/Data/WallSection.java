package com.rage.clamber.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Class to hold Wall Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallSection {

    protected String name;
    protected int id;
    protected Date dateLastUpdated;
    protected boolean isTopout;
    protected int mainWallId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public boolean isTopout() {
        return isTopout;
    }

    public void setIsTopout(boolean isTopout) {
        this.isTopout = isTopout;
    }

    public int getMainWallId() {
        return mainWallId;
    }

    public void setMainWallId(int mainWallId) {
        this.mainWallId = mainWallId;
    }
}
