package com.example.admin_wecarcare.Database;

import android.provider.BaseColumns;

public final class UserProfile {
    private UserProfile() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "UserInfo";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD= "password";
    }
}
