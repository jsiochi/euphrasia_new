package com.jbj.euphrasia.activities;
 
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jbj.euphrasia.R;
 
public class RemoteFragment extends Fragment {
     
    public RemoteFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_remote, container, false);
          
        return rootView;
    }
}