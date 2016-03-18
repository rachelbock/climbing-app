package com.rage.clamber.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to create the local user database.
 */
public class UserSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    private static UserSQLiteHelper userSQLiteHelper;

    public UserSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /**
     * Returns an instance of the UserSQLiteHelper.
     * @param context - must be application context.
     * @return an instance of the UserSQLiteHelper.
     */
    public static UserSQLiteHelper getInstance(Context context) {
        if (userSQLiteHelper == null) {
            userSQLiteHelper = new UserSQLiteHelper(context.getApplicationContext(), DATABASE_NAME, null, 1);
        }
        return userSQLiteHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.SQL_CREATE_USERDATA);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(User.SQL_DELETE_USERDATA);
        onCreate(db);
    }
}
