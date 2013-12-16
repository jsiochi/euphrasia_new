package com.jbj.euphrasia.activities;
 
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.MainActivity;
 
public class MainFragment extends Fragment {
     
    public MainFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }
}