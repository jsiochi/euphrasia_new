package dialog_fragments;

import com.jbj.euphrasia.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class CreatePhrasebookDialog extends EntryDialogFragment {
	
	private EditText myTitleTextView;
	
	public CreatePhrasebookDialog(){
		//empty required
	}

	@Override
	public int getFragmentID() {
		return R.layout.create_phrasebook;
	}
	
	@Override
	protected void useView(View view){
		myTitleTextView = (EditText)view.findViewById(R.id.new_phrasebook_name);
	}

	@Override
	public Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater) {
		View view = inflater.inflate(this.getFragmentID(), null);
		this.useView(view);
        builder.setView(view)
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // create a new phrasebook with user data
                	   mySourceSpinner.onCreated(myTitleTextView.getText());
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

}
