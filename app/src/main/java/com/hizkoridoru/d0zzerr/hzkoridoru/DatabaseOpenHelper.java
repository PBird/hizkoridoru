package com.hizkoridoru.d0zzerr.hzkoridoru;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;



/**
 * Created by sergen on 03.01.2018.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }





}
