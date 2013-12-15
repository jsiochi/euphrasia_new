package com.jbj.euphrasia.dialog_fragments;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.SearchActivity;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class DeleteAlertDialog extends AbstractDialog {
	
	private long myDeleteID;

	@Override
	public Dialog makeButtons(Builder builder, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.delete_alert,null);
        builder.setView(view)
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       SearchActivity search = (SearchActivity)mySourceActivity;
                       search.deleteEntry(id);
                       dialog.dismiss();
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                   }
               });
        return builder.create();
	}
	
	public void setDeleteID(long id){
		myDeleteID = id;
	}

}
