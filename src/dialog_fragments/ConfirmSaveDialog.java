package dialog_fragments;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.EntryActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

public class ConfirmSaveDialog extends EntryDialogFragment {
	
	
	public ConfirmSaveDialog(){
		//empty required
	}

	@Override
	public int getFragmentID() {
		return R.layout.confirm_save;
	}
	

	@Override
	public Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater) {
		// Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(this.getFragmentID(), null))
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mySourceActivity.confirmSave();
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

	@Override
	protected void useView(View view) {
		// do nothing
	}


}
