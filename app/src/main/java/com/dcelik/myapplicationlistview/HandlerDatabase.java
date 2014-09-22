package com.dcelik.myapplicationlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dcelik on 9/18/14.
 */
public class HandlerDatabase {

    //Database Model
    private ModelDatabase model;

    //Database
    private SQLiteDatabase database;

    //All Fields
    private String[] allColumns = {
            ModelDatabase.CHAT_ID,
            ModelDatabase.CHAT_NAME,
            ModelDatabase.CHAT_MESSAGE,
            ModelDatabase.CHAT_IMAGE
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){
        model = new ModelDatabase(context);
    }

    /**
     * Add
     */
    public void addChatToDatabase(String id, String name, String message, byte[] image){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.CHAT_ID, id);
        values.put(ModelDatabase.CHAT_NAME, name);
        values.put(ModelDatabase.CHAT_TIME, Calendar.getInstance().get(Calendar.SECOND));
        values.put(ModelDatabase.CHAT_MESSAGE, message);
        values.put(ModelDatabase.CHAT_IMAGE, image);
        database.insertWithOnConflict(ModelDatabase.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    public void updateChat(Chat chat){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.CHAT_ID, chat.id);
        values.put(ModelDatabase.CHAT_NAME, chat.name);
        values.put(ModelDatabase.CHAT_TIME, chat.time);
        values.put(ModelDatabase.CHAT_MESSAGE, chat.message);
        values.put(ModelDatabase.CHAT_IMAGE, chat.image);
        database.update(ModelDatabase.TABLE_NAME, values, ModelDatabase.CHAT_ID, null);
    }

    /**
     * Get
     */
    public ArrayList<Chat> getAllChats(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns,null, null, null, null, null));
    }

    public Chat getChatByID(String id){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.CHAT_ID + " like '%" + id + "%'",
                null, null, null, null
        )).get(0);
    }

    /**
     * Delete
     */
    public void deleteChatById(String id){
        database.delete(
                ModelDatabase.TABLE_NAME,
                ModelDatabase.CHAT_ID + " like '%" + id + "%'",
                null
        );
    }

    /**
     * Additional Helpers
     */
    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<Chat> sweepCursor(Cursor cursor){
        ArrayList<Chat> myChats = new ArrayList<Chat>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Kitty
            Chat myChat = new Chat(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)//,
                    //cursor.getBlob(6)
            );
            //Add the Kitty
            myChats.add(myChat);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return myChats;
    }

    //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}

