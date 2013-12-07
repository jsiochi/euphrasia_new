package dialog_fragments;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.EntryActivity;
import com.jbj.euphrasia.fields.PhrasebookField;
import com.jbj.euphrasia.spinners.EuphrasiaSpinner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public abstract class EntryDialogFragment extends DialogFragment {
	
	protected EntryActivity mySourceActivity;
	protected EuphrasiaSpinner mySourceSpinner;
	
	public EntryDialogFragment(){
		//empty
	}
	
	public void setSourceSpinner(EuphrasiaSpinner source){
		mySourceSpinner = source;
	}
	
	public void setSourceActivity(EntryActivity activity){
		mySourceActivity = activity;
	}
	
	public abstract int getFragmentID();
	
	public abstract Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater);
	
	protected abstract void useView(View view);

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        return this.makeButtons(builder, inflater);
    }
}
