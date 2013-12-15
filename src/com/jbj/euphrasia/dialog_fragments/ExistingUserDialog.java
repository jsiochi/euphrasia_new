package com.jbj.euphrasia.dialog_fragments;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.LoginActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ExistingUserDialog extends AbstractDialog {
	
	protected EditText myNameField;
	protected EditText myPasswordField;
	protected boolean rememberMe = false;
	
	public void useView(View view){
		myNameField = (EditText) view.findViewById(R.id.account_name);
		myPasswordField = (EditText) view.findViewById(R.id.account_password);
	}
	
	
	
	@Override
	public Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.existing_user, null);
		this.useView(view);
		
        builder.setView(view);
        // Add action buttons
               builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // create a new phrasebook with user data
                	   EditText[] fields = {myNameField,myPasswordField};
                	   if(myNameField.getText().toString().isEmpty()||myPasswordField.getText().toString().isEmpty()){
//                		   for(int i=0;i<fields.length;i++){
//                			   if(fields[i].getText().toString().length()==0){
//                				   fields[i].setHintTextColor(R.color.red);
//                			   }
//                		   }
                		   Toast.makeText(mySourceActivity, "Please enter all required fields", Toast.LENGTH_LONG).show();
                	   }
                	   else{
	                	   String accountName = myNameField.getText().toString();
	                	   String accountPassword = myPasswordField.getText().toString();
	                	   LoginActivity login = (LoginActivity)mySourceActivity;
	                	   login.onLoginAttempt(accountName, accountPassword);
	                	   dialog.dismiss();
                	   }
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                   }
               });
        	   
        return builder.create();
	}

}
