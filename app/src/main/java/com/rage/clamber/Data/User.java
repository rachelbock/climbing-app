package com.rage.clamber.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to hold data on an individual user.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements BaseColumns, Parcelable {

    private String userName;
    private int height;
    private int skillLevel;
    private int id;



    public User() {
        // No-arg constructor for jackson
    }

    public User(String userName, int userHeight, int userSkillLevel) {
        this.userName = userName;
        height = userHeight;
        skillLevel = userSkillLevel;
    }

    public User(String userName, int userHeight, int userSkillLevel, int userID) {
        this.userName = userName;
        height = userHeight;
        skillLevel = userSkillLevel;
        id = userID;
    }

    protected User(Parcel in) {
        userName = in.readString();
        height = in.readInt();
        skillLevel = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeInt(height);
        dest.writeInt(skillLevel);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}


