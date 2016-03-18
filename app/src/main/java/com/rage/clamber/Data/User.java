package com.rage.clamber.Data;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Class to hold data on an individual user.
 */
public class User implements BaseColumns {

    private String name;
    private int height;
    private int skillLevel;
    private int id;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "name";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_SKILL_LEVEL = "skill_level";
    public static final String COLUMN_USER_ID = "id";

    public static final String SQL_CREATE_USERDATA = "CREATE TABLE " + User.TABLE_NAME + " (" +
            User.COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
            User.COLUMN_USERNAME + " TEXT, " +
            User.COLUMN_HEIGHT + " INTEGER, " +
            User.COLUMN_SKILL_LEVEL + " INTEGER)";

    public static final String SQL_DELETE_USERDATA = "DROP TABLE IF EXISTS " + User.TABLE_NAME;

    public User(String userName, int userHeight, int userSkillLevel) {
        name = userName;
        height = userHeight;
        skillLevel = userSkillLevel;
    }

    public User(String userName, int userHeight, int userSkillLevel, int userID) {
        name = userName;
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
        contentValues.put(COLUMN_HEIGHT, height);
        contentValues.put(COLUMN_SKILL_LEVEL, skillLevel);
        contentValues.put(COLUMN_USER_ID, id);
        return contentValues;
    }
}


