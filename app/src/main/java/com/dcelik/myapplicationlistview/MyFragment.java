package com.dcelik.myapplicationlistview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dcelik on 9/11/14.
 */
public class MyFragment extends Fragment{
    public MyFragment() {
    }

    String numid = "";
    String username = "Filippos";
    TimeZone EZT = TimeZone.getTimeZone("GMT-4");

    public void setUsername(String str){
        username = str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final EditText myText = (EditText) rootView.findViewById(R.id.text_to_add);
        Button myButton = (Button) rootView.findViewById(R.id.add_button);
        final ListView myListView = (ListView) rootView.findViewById(R.id.list_view);

        final ArrayList<Chat> chats = new ArrayList<Chat>();
        final ChatAdapter myAdapter = new ChatAdapter(getActivity(), chats);
        myButton.setText(R.string.button_press);
        myButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Calendar c = Calendar.getInstance(EZT,Locale.US);
                String secs = String.valueOf(c.get(Calendar.SECOND));
                String mins = String.valueOf(c.get(Calendar.MINUTE));
                String hrs = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                if(c.get(Calendar.SECOND)<10){
                     secs = "0"+secs;
                }
                if(c.get(Calendar.MINUTE)<10){
                    mins = "0"+mins;
                }
                if(c.get(Calendar.HOUR_OF_DAY)<10){
                    hrs = "0"+hrs;
                }
                String msg = myText.getText().toString();
                if(msg.length()>0) {
                    String date = String.valueOf(hrs + ":" + mins + ":" + secs + " " +
                            c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " +
                            c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR));
                    Chat toAdd = new Chat(numid, username, date, myText.getText().toString());
                    myAdapter.add(toAdd);
                    myText.setText("");
                    myAdapter.notifyDataSetChanged();
                    myListView.setSelection(chats.size());
                }
            }
        });

        myListView.setAdapter(myAdapter);


        return rootView;
    }
}
