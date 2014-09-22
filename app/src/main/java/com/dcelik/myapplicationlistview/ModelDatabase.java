package com.dcelik.myapplicationlistview;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by chris on 12/23/13.
 */
public class ModelDatabase extends SQLiteOpenHelper {
    //Table Name
    public static final String TABLE_NAME = "ChatLogs";

    //Table Fields
    public static final String CHAT_ID = "ID";
    public static final String CHAT_NAME = "name";
    public static final String CHAT_TIME = "time";
    public static final String CHAT_MESSAGE = "message";
    //public static final String CHAT_IMAGE = "image";

    //Database Info
    private static final String DATABASE_NAME = "ChatAppDatabase";
    private static final int DATABASE_VERSION = 1;

    // ModelDatabase creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + CHAT_ID + " TEXT NOT NULL UNIQUE, "
            + CHAT_NAME + " TEXT NOT NULL, "
            + CHAT_TIME + " TEXT NOT NULL, "
            + CHAT_MESSAGE + " TEXT NOT NULL );";
            //+ CHAT_IMAGE + " BLOB );";

    //Default Constructor
    public ModelDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //OnCreate Method - creates the ModelDatabase
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);

    }
    @Override
    //OnUpgrade Method - upgrades ModelDatabase if applicable
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(ModelDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}