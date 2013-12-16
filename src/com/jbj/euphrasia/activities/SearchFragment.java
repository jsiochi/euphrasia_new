package com.jbj.euphrasia.activities;
 
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jbj.euphrasia.R;
 
public class SearchFragment extends Fragment {
     
    public SearchFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
          
        return rootView;
    }
}