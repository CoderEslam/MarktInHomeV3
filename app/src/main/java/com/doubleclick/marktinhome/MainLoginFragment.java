package com.doubleclick.marktinhome;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.doubleclick.marktinhome.ui.Splash.Splash4Fragment;


public class MainLoginFragment extends BaseFragment {


    FrameLayout frameLayout;

    public MainLoginFragment() {
        // Required empty public constructor
    }

    public static MainLoginFragment newInstance(String param1, String param2) {
        MainLoginFragment fragment = new MainLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_login, container, false);
        frameLayout = view.findViewById(R.id.mainLoginFragment);
        try {
            StartFragment(frameLayout.getId(), new Splash4Fragment());
        }catch (Exception e){

        }

        return view;
    }


}