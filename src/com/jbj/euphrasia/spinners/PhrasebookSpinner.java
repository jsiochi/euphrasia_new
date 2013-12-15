package com.jbj.euphrasia.spinners;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.activities.EntryActivity;
import com.jbj.euphrasia.dialog_fragments.CreatePhrasebookDialog;
import com.jbj.euphrasia.dialog_fragments.EntryDialogFragment;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.PhrasebookField;
import com.jbj.euphrasia.R;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class PhrasebookSpinner extends EuphrasiaSpinner {

	public PhrasebookSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public Uri getColumnUri() {
		return EntryProvider.CONTENT_PHRASEBOOKS_URI;
	}

	@Override
	protected String getColumnName() {
		return EntryColumns.COLUMN_NAME_PHRASEBOOK;
	}

	@Override
	protected int getArrayData() {
		if(canCreateItems)
			return R.array.test_phrasebooks;
		return R.array.test_phrasebooks_no_edit;
	}

	@Override
	protected String getLogString() {
		return "NEW_PHRASEBOOK_ID";
	}

	@Override
	public OnItemSelectedListener retrieveOnItemSelectedListener() {
		return new PhrasebookSelectListener();
	}

	@Override
	protected String[] getFroms() {
		return new String[]{EntryColumns.COLUMN_NAME_PHRASEBOOK, EntryColumns._ID};
	}
	
	@Override
	protected String getDefaultSpinnerMessage() {
		return "Choose Phrasebook";
	}
	
	@Override
	protected EntryDialogFragment getDialogFragment() {
		return new CreatePhrasebookDialog();
	}
	
	@Override
	protected String getDialogLayout() {
		return "create_phrasebook";
	}
	
	@Override 
	protected Field createField(String selected){
		return new PhrasebookField(selected);
	}
	
	private class PhrasebookSelectListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			doSelect(parent,view,position,id);
			if(mySourceActivity instanceof EntryActivity){
				((EntryActivity)mySourceActivity).setPhrasebook(parent.getSelectedItem().toString());
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

	@Override
	public String getAction() {
		return ACTION_BROWSE_PHRASEBOOKS;
	}

	@Override
	public String getColumnKey() {
		return EXTRA_PHRASEBOOK_KEY;
	}

}
