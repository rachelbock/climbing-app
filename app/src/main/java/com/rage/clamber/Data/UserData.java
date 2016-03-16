package com.rage.clamber.Data;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Class to hold data on an individual user.
 */
public class UserData implements BaseColumns {

    private String name;
    private String gender;
    private int height;
    private int skillLevel;
    private int id;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_HEIGHT = "height (inches)";
    public static final String COLUMN_SKILL_LEVEL = "skill level";
    public static final String COLUMN_USER_ID = "id";

    public UserData(String userName, String userGender, int userHeight, int userSkillLevel) {
        name = userName;
        gender = userGender;
        height = userHeight;
        skillLevel = userSkillLevel;
    }

    public UserData(String userName, String userGender, int userHeight, int userSkillLevel, int userID) {
        name = userName;
        gender = userGender;
        height = userHeight;
        skillLevel = userSkillLevel;
        id = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, name);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_HEIGHT, height);
        contentValues.put(COLUMN_SKILL_LEVEL, skillLevel);
        contentValues.put(COLUMN_USER_ID, id);
        return contentValues;
    }
}


