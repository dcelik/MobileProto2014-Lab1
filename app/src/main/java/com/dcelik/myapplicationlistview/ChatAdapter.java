package com.dcelik.myapplicationlistview;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcelik on 9/11/14.
 */
public class ChatAdapter extends ArrayAdapter<String>{
    public ChatAdapter(Context context, int resource, List<String> object){
        super(context,resource,object);
    }
}
