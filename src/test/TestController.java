package test;

import android.content.Context;
import android.test.AndroidTestCase;
import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryDatabaseManager;
import com.jbj.euphrasia.fields.AudioField;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.NativeTextField;

public class TestController extends AndroidTestCase {

	private Controller myController;
	private Field myAudioField = new AudioField("THIS IS MY FILE PATH");
	private EntryDatabaseManager myEntryManager;
	private Context myContext;

	protected void setUp() throws Exception {
		super.setUp();
		myContext = this.getContext();
		myController = new Controller(myContext);
		myEntryManager = new EntryDatabaseManager(myContext);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testUpdateEntryField(){
		NativeTextField nativeText = new NativeTextField("TEST");
		nativeText.updateEntryField(myEntryManager);
		assertEquals(nativeText,myEntryManager.getNativeText());
	}
	
	public void testGetAudioPath(){
		myController.updateEntryField(myAudioField);
		assertEquals("THIS IS MY FILE PATH",myController.getAudioPath());
	}
	

}
