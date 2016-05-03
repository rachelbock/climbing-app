package com.rage.clamber.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Displays climbs in database
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Climb implements Parcelable{

    private int climbId;
    private int gymRating;
    private String tapeColor;
    private int wallId;
    private boolean isProject;
    private boolean isCompleted;
    private String type;

    protected Climb(Parcel in) {
        climbId = in.readInt();
        gymRating = in.readInt();
        tapeColor = in.readString();
        wallId = in.readInt();
        isProject = in.readByte() != 0;
        isCompleted = in.readByte() != 0;
        type = in.readString();
    }

    public Climb() {
        // No-arg constructor for jackson
    }

    public static final Creator<Climb> CREATOR = new Creator<Climb>() {
        @Override
        public Climb createFromParcel(Parcel in) {
            return new Climb(in);
        }

        @Override
        public Climb[] newArray(int size) {
            return new Climb[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(climbId);
        dest.writeInt(gymRating);
        dest.writeString(tapeColor);
        dest.writeInt(wallId);
        dest.writeByte((byte) (isProject ? 1 : 0));
        dest.writeByte((byte) (isCompleted ? 1 : 0));
        dest.writeString(type);
    }
}
