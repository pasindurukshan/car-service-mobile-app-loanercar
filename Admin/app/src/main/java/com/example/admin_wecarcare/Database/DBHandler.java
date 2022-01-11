package com.example.admin_wecarcare.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.Users.COLUMN_NAME_USERNAME + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;





    //Insert
    public long addInfo(String username, String password){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME_USERNAME, username);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return newRowId;
    }


    //Update
    public Boolean updateInfo(String username, String password){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME_USERNAME, username);
        values.put(UserProfile.Users.COLUMN_NAME_USERNAME, username);

        // Which row to update, based on the title
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { username };

        int count = db.update(
                UserProfile.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count >=1){
            return true;
        }
        else{
            return false;
        }
    }


        //Delete
        public void deleteInfo(String username){

            SQLiteDatabase db = getWritableDatabase();

            // Define 'where' part of query.
            String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " LIKE ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { username };
            // Issue SQL statement.
            int deletedRows = db.delete(UserProfile.Users.TABLE_NAME, selection, selectionArgs);
        }



        //Read
        public List readAllInfo(){

            String username = "pasindu";
            SQLiteDatabase db = getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    BaseColumns._ID,
                    UserProfile.Users.COLUMN_NAME_USERNAME,
                    UserProfile.Users.COLUMN_NAME_PASSWORD
            };

            // Filter results WHERE "title" = 'My Title'
            String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";
            String[] selectionArgs = { username };

            // How you want the results sorted in the resulting Cursor
            String sortOrder =
                    UserProfile.Users.COLUMN_NAME_USERNAME + " ASC";

            Cursor cursor = db.query(
                    UserProfile.Users.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );

            List usernames = new ArrayList<>();
            while(cursor.moveToNext()) {
                String user = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_USERNAME));
                usernames.add(user);
            }
            cursor.close();
            return usernames;
        }



        //Search
        public List readAllInfo(String username){

            SQLiteDatabase db = getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    BaseColumns._ID,
                    UserProfile.Users.COLUMN_NAME_USERNAME,
                    UserProfile.Users.COLUMN_NAME_PASSWORD
            };

            // Filter results WHERE "title" = 'My Title'
            String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " LIKE ?";
            String[] selectionArgs = { username };

            // How you want the results sorted in the resulting Cursor
            String sortOrder =
                    UserProfile.Users.COLUMN_NAME_USERNAME + " ASC";

            Cursor cursor = db.query(
                    UserProfile.Users.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );

            List UserInfo = new ArrayList<>();
            while(cursor.moveToNext()) {
                String user = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_USERNAME));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_PASSWORD));
                UserInfo.add(user);//0
                UserInfo.add(pass);//1
            }
            cursor.close();
            return UserInfo;
        }



}