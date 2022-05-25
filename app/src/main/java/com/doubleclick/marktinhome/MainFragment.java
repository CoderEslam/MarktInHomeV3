package com.doubleclick.marktinhome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.doubleclick.marktinhome.ui.Splash.Splash4Fragment;


public class MainFragment extends BaseFragment {


    FrameLayout frameLayout;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        frameLayout = view.findViewById(R.id.mainFragment);
        try {
            StartFragment(frameLayout.getId(), new Splash4Fragment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


}