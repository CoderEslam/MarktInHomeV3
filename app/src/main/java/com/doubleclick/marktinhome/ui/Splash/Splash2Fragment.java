package com.doubleclick.marktinhome.ui.Splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Splash2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Splash2Fragment extends BaseFragment {


    public Splash2Fragment() {
        // Required empty public constructor
    }

    public static Splash2Fragment newInstance(String param1, String param2) {
        Splash2Fragment fragment = new Splash2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_splash2, container, false);
        return view;
    }
}