package dialog_fragments;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.EntryActivity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmSaveDialog extends DialogFragment {
	
	private EntryActivity mySourceActivity;
	
	public ConfirmSaveDialog(){
		//empty required
	}
	
	public void setSourceActivity(EntryActivity activity){
		mySourceActivity = activity;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_save, container);
        //myMessage = (EditText) view.findViewById(R.id.confirm_save);
        getDialog().setTitle("NOTICE");
        Button confirmButton = ((Button) this.getDialog().findViewById(R.id.confirm_save_button));
        confirmButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mySourceActivity.confirmSave();
			}
        	
        });
        return view;
    }

}
