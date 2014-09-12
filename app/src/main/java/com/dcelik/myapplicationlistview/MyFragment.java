package com.dcelik.myapplicationlistview;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dcelik on 9/11/14.
 */
public class MyFragment extends Fragment{
    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final EditText myText = (EditText) rootView.findViewById(R.id.text_to_add);
        Button myButton = (Button) rootView.findViewById(R.id.add_button);
        final ListView myListView = (ListView) rootView.findViewById(R.id.my_list_view);

        final ArrayList<String> listchats = new ArrayList<String>();
        final ChatAdapter myAdapter = new ChatAdapter(getActivity(), R.layout.chat_item,listchats);

        myButton.setText(R.string.button_press);
        myButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myAdapter.add(myText.getText().toString());
                myText.setText("");
                myAdapter.notifyDataSetChanged();
                myListView.setSelection(listchats.size());
            }
        });



        myListView.setAdapter(myAdapter);

        return rootView;
    }
}
