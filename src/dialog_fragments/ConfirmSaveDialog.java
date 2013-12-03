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
	
////	@Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////            Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.confirm_save, container);
////        //myMessage = (EditText) view.findViewById(R.id.confirm_save);
////        getDialog().setTitle("NOTICE");
//<<<<<<< HEAD
////        Button confirmButton = ((Button) view.findViewById(R.id.confirm_save_button));
//=======
////        Button confirmButton = ((Button) view.findViewById(R.id.confirm_save_button_id));
//>>>>>>> 8a76d93353cc9c951210635a959f50c575689606
////        confirmButton.setOnClickListener(new OnClickListener(){
////
////			@Override
////			public void onClick(View v) {
////				mySourceActivity.confirmSave();
////			}
////        	
////        });
////        return view;
////    }
//<<<<<<< HEAD
//=======
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.confirm_save, null))
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mySourceActivity.confirmSave();
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
