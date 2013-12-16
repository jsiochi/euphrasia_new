package com.jbj.euphrasia.activities;
 
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jbj.euphrasia.R;
 
public class SettingsFragment extends Fragment {
     
    public SettingsFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
          
        return rootView;
    }
}