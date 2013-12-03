package com.jbj.euphrasia.fields;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.id;

public class FieldFactory {
	
	private Map<Integer,String> myFieldCatalog;
	
	public FieldFactory(){
		myFieldCatalog = new HashMap<Integer,String>();
		myFieldCatalog.put(R.id.foreign_text, "com.jbj.euphrasia.fields.ForeignTextField");
		myFieldCatalog.put(R.id.native_text, "com.jbj.euphrasia.fields.NativeTextField");
		myFieldCatalog.put(R.id.edit_tags, "com.jbj.euphrasia.fields.TagField");
		myFieldCatalog.put(R.id.edit_language,"com.jbj.euphrasia.fields.LanguageField");
		myFieldCatalog.put(R.id.edit_title,"com.jbj.euphrasia.fields.TitleField");
	}

	public Field createField(int componentID, String data){
		/*
		 * uses reflection and id of .xml GUI component to create the right subclass. 
		 * EXAMPLE
		 * @param: "foreign_text"
		 * @return: ForeignTextField
		 */
		String className = myFieldCatalog.get(componentID);
		try {
			Class fieldClass = Class.forName(className);
			Constructor fieldConstructor = fieldClass.getConstructor(String.class);
			Field field = (Field) fieldConstructor.newInstance(data);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return new NullField();
		}
	}
	
	
}
