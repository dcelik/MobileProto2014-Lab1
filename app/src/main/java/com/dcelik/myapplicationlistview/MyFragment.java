package com.dcelik.myapplicationlistview;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Created by dcelik on 9/11/14.
 */
public class MyFragment extends Fragment{
    public MyFragment() {
    }

    Firebase fb;
    HandlerDatabase db;
    ChatAdapter chatAdapter;
    ListView listView;
    int numid = 0;
    String username = "Deniz";
    TimeZone EZT = TimeZone.getTimeZone("GMT-4");

    public void setUsername(String str){
        username = str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = ((MainActivity)getActivity()).db;
        fb = ((MainActivity)getActivity()).fb;
        int max = 0;
        for (Chat c:db.getAllChats()) {
            max = Math.max(new Integer(c.getId()),max);
        }
        numid = max+1;
        //db.deleteAllChats();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final EditText myText = (EditText) rootView.findViewById(R.id.text_to_add);
        Button myButton = (Button) rootView.findViewById(R.id.add_button);
        listView = (ListView) rootView.findViewById(R.id.list_view);

        final ArrayList<Chat> chats = db.getAllChats();
        chatAdapter = new ChatAdapter(getActivity(), chats);

        myButton.setText(R.string.button_press);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance(EZT, Locale.US);
                String secs = String.valueOf(c.get(Calendar.SECOND));
                String mins = String.valueOf(c.get(Calendar.MINUTE));
                String hrs = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                if (c.get(Calendar.SECOND) < 10) {
                    secs = "0" + secs;
                }
                if (c.get(Calendar.MINUTE) < 10) {
                    mins = "0" + mins;
                }
                if (c.get(Calendar.HOUR_OF_DAY) < 10) {
                    hrs = "0" + hrs;
                }
                String msg = myText.getText().toString();
                if (msg.length() > 0) {
                    String date = String.valueOf(hrs + ":" + mins + ":" + secs + " " +
                            c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " +
                            c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR));
                    //Chat toAdd = new Chat(numid, username, date, msg);
                    int max = 0;
                    for (Chat ch:db.getAllChats()) {
                        max = Math.max(new Integer(ch.getId()),max);
                    }
                    numid = max+1;
                    db.addChatToDatabase(String.valueOf(numid), username, date, msg);
                    refreshFb();
                    numid++;
                    //myAdapter.add(toAdd);
                    myText.setText("");
                    chatAdapter.clear();
                    chatAdapter.addAll(db.getAllChats());
                    chatAdapter.notifyDataSetChanged();
                    listView.setSelection(numid);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                new EditingText(getActivity(), new StringCallback() {
                    @Override
                    public void handleString(String value) {
                        Chat chatEdit = (Chat) parent.getItemAtPosition(position);
                        chatEdit.setMessage(value);
                        db.updateChat(chatEdit);
                        chatAdapter.notifyDataSetChanged();
                        refreshFb();
                    }

                    @Override
                    public void handleDelete() {
                        Chat chatDeleted = (Chat) parent.getItemAtPosition(position);
                        db.deleteChatById(chatDeleted.getId());
                        chatAdapter.clear();
                        chatAdapter.addAll(db.getAllChats());
                        chatAdapter.notifyDataSetChanged();
                        listView.setSelection(numid);
                        refreshFb();
                    }
                }).show();
            }
        });
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Chat> allchats= new ArrayList<Chat>();
                Map<String, Object> fbchats = (Map<String, Object>) snapshot.getValue();
                if(fbchats!=null){
                    ArrayList<Object> values = new ArrayList<Object>();
                    values.addAll(fbchats.values());
                    for(Object obj:values){
                        HashMap<String,Object> chat = (HashMap<String,Object>) obj;
                        ArrayList<Object> vals = new ArrayList<Object>();
                        vals.addAll(chat.values());
                        String message = (String) vals.get(0);
                        String id = (String) vals.get(1);
                        String time = (String) vals.get(2);
                        String name = (String) vals.get(3);
                        Chat toAdd = new Chat(id,name,time,message);
                        allchats.add(toAdd);
                    }
                    Collections.sort(allchats, new Comparator<Chat>() {
                        @Override
                        public int compare(Chat z1, Chat z2) {
                            if (new Integer(z1.getId()) > new Integer(z2.getId()))
                                return 1;
                            if (new Integer(z1.getId()) < new Integer(z2.getId()))
                                return -1;
                            return 0;
                        }
                    });
                    db.deleteAllChats();
                    int max = 0;
                    for(Chat c:allchats){
                        db.addChatToDatabase(c.getId(),c.getName(),c.getTime(),c.getMessage());
                        max = Math.max(new Integer(c.getId()),max);

                    }
                    chatAdapter.addAll(db.getAllChats());
                    listView.setSelection(chatAdapter.getCount());
                    numid = max+1;
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
                listView.setAdapter(chatAdapter);
        return rootView;
    }

    //When the Fragment is started
    @Override
    public void onStart() {
        super.onStart();
        chatAdapter.notifyDataSetChanged();
    }

    public void refreshFb(){
        fb.setValue(null);
        ArrayList<Chat> chats = db.getAllChats();
        for (Chat ch : chats){
            Map<String, String> fbchat = new HashMap<String, String>();
            fbchat.put("id",ch.getId());
            fbchat.put("name",ch.getName());
            fbchat.put("time",ch.getTime());
            fbchat.put("message",ch.getMessage());
            fb.push().setValue(fbchat);
        }
    }

    //When the Fragment is resumed
    @Override
    public void onResume() {
        super.onResume();
        chatAdapter.notifyDataSetChanged();
    }
}
