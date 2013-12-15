package com.jbj.euphrasia.dialog_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.LoginActivity;

public abstract class AbstractDialog extends DialogFragment {
	
	protected Activity mySourceActivity;
	
	public AbstractDialog(){
		super();
		//keep empty
	}
	
	public void setSourceActivity(Activity activity){
		mySourceActivity = activity;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        return this.makeButtons(builder, inflater);
    }
	
	public abstract Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater);
	
	

}
