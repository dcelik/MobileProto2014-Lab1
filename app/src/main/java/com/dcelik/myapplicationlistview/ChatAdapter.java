package com.dcelik.myapplicationlistview;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcelik on 9/11/14.
 */
public class ChatAdapter extends ArrayAdapter{

    //ArrayList Containing the Image Urls
    ArrayList<Chat> addedChats;

    //Parent Activity
    Activity activity;

    //Public Constructor
    public ChatAdapter(Activity activity, ArrayList<Chat> addedChats){
        super(activity, R.layout.chat_item, addedChats);
        this.addedChats = addedChats;
        this.activity = activity;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Chat chat: this.addedChats) {
            sb.append("Chat ");
            sb.append(chat.id);
            sb.append(" at ");
            sb.append(chat.time);
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){ //Grab View if haven't already
            convertView = activity.getLayoutInflater().inflate(R.layout.chat_item,null);
        }

        //Set Image based on BitMap
        Chat selectedChat = addedChats.get(position);

        //Using Drawable is enough
        ImageView image = (ImageView) convertView.findViewById(R.id.chat_image);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        image.setImageBitmap(BitmapFactory.decodeByteArray(selectedChat.image, 0, selectedChat.image.length));
        image.setImageDrawable(activity.getResources().getDrawable(R.drawable.androidguy));
        return convertView;
    }
}
