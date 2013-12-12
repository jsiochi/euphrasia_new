package com.jbj.euphrasia;

import java.util.HashMap;
import java.util.List;
 
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
 
public class CustomCursorAdapter extends SimpleCursorAdapter {
 
    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
    	super(context,layout,c,from,to);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = super.getView(position, convertView, parent);
      if ( position % 2 == 0)
          view.setBackgroundResource(R.drawable.search_even_row);
      else
          view.setBackgroundResource(R.drawable.search_odd_row);
      return view;
    }
}