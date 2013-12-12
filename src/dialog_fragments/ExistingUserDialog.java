package dialog_fragments;

import com.jbj.euphrasia.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ExistingUserDialog extends AbstractDialog {
	
	protected EditText myNameField;
	protected EditText myPasswordField;
	
	public void useView(View view){
		myNameField = (EditText) view.findViewById(R.id.account_name);
		myPasswordField = (EditText) view.findViewById(R.id.account_password);
	}
	
	@Override
	public Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.existing_user, null);
		this.useView(view);
        builder.setView(view)
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // create a new phrasebook with user data
                	   String accountName = myNameField.getText().toString();
                	   String accountPassword = myPasswordField.getText().toString();
                	   mySourceActivity.onLoginAttempt(accountName, accountPassword);
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
