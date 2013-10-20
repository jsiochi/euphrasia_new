package com.jbj.euphrasia;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class FieldFactory {
	
	private Map<String,String> myFieldCatalog;
	
	public FieldFactory(){
		myFieldCatalog = new HashMap<String,String>();
		myFieldCatalog.put("foreign_text", "ForeignTextField");
		myFieldCatalog.put("native_text", "NativeTextField");
	}

	public Field createField(String componentID, String data){
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
