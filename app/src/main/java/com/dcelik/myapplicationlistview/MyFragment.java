package com.dcelik.myapplicationlistview;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by dcelik on 9/11/14.
 */
public class MyFragment extends Fragment{
    public MyFragment() {
    }

    String numid = "";
    String username = "";
    Calendar c = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //FIXME - NOT NECESSARY
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.androidguy);
//        int bytes = bmp.getByteCount();
//        ByteBuffer buffer = ByteBuffer.allocate(bytes);
//        byte[] array = buffer.array();
//        Log.i("DebugDebug", Arrays.toString(array));

        final EditText myText = (EditText) rootView.findViewById(R.id.text_to_add);
        Button myButton = (Button) rootView.findViewById(R.id.add_button);
        final ListView myListView = (ListView) rootView.findViewById(R.id.list_view);

        final ArrayList<Chat> chats = new ArrayList<Chat>();
        final ChatAdapter myAdapter = new ChatAdapter(getActivity(), chats);
        myButton.setText(R.string.button_press);
        myButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Chat toAdd = new Chat(numid,username,String.valueOf(c.get(Calendar.SECOND)),myText.getText().toString(), bmp);
                Chat toAdd = new Chat(numid,username,String.valueOf(c.get(Calendar.SECOND)),myText.getText().toString());
                myAdapter.add(toAdd);
                myText.setText("");
                myAdapter.notifyDataSetChanged();
                myListView.setSelection(chats.size());
            }
        });

        myListView.setAdapter(myAdapter);


        return rootView;
    }
}
