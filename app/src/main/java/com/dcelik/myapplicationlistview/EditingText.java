package com.dcelik.myapplicationlistview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by dcelik on 9/29/14.
 */
public class EditingText extends AlertDialog.Builder {


    public EditingText(final Context context, final StringCallback callback) {
        super(context);
        final android.widget.EditText input = new android.widget.EditText(context);
        setTitle("Editing Text")
                .setView(input)
                .setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newText = input.getText().toString();
                        if (newText.equals("f***")) {
                            Toast.makeText(context, "Don't use the f word when texting!", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        Toast.makeText(context, "Your message is now updated to " + newText, Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        callback.handleString(newText);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton(R.string.delete, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        callback.handleDelete();
                        Toast.makeText(context, "Your message was deleted", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onClick(DialogInterface dialog, int id) {

        //...

        ;
    }
}
