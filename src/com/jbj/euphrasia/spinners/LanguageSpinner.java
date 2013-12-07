package com.jbj.euphrasia.spinners;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.LanguageField;
import com.jbj.euphrasia.fields.PhrasebookField;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;

import dialog_fragments.EntryDialogFragment;
import dialog_fragments.NewLanguageDialog;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

public class LanguageSpinner extends EuphrasiaSpinner {

	public LanguageSpinner(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	@Override
	public Uri getColumnUri() {
		return EntryProvider.CONTENT_LANGUAGES_URI;
	}

	@Override
	protected String getColumnName() {
		return EntryColumns.COLUMN_NAME_LANGUAGE;
	}

	@Override
	protected int getArrayData() {
		return R.array.languages;
	}

	@Override
	protected String getLogString() {
		return "NEW_PHRASEBOOK_ID";
	}
	
	@Override
	protected String getDefaultSpinnerMessage(){
		return "Choose Language";
	}
	
	@Override
	protected EntryDialogFragment getDialogFragment() {
		return new NewLanguageDialog();
	}

	@Override
	public OnItemSelectedListener retrieveOnItemSelectedListener() {
		return new LanguageSelectListener();
	}

	@Override
	protected String[] getFroms() {
		return new String[]{EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns._ID};
	}
	
	@Override 
	protected Field createField(String selected){
		return new LanguageField(selected);
	}
	
	@Override
	protected String getDialogLayout() {
		return "new_language";
	}
	
	private class LanguageSelectListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			doSelect(parent,view,position,id);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
