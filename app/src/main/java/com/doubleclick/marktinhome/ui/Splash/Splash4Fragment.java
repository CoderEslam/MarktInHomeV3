package com.doubleclick.marktinhome.ui.Splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.ui.Login.LoginFragment;
import com.doubleclick.marktinhome.ui.Login.RegisterFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Splash4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Splash4Fragment extends BaseFragment {

    private FrameLayout mainFragment;
    private TextView SignIn,NewUser;

    public Splash4Fragment() {
        // Required empty public constructor
    }

    public static Splash4Fragment newInstance(String param1, String param2) {
        Splash4Fragment fragment = new Splash4Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash4, container, false);
        mainFragment = getActivity().findViewById(R.id.mainLoginFragment);
        SignIn = view.findViewById(R.id.SignIn);
        NewUser = view.findViewById(R.id.NewUser);
        SignIn.setOnClickListener(v -> {
            try {
                StartFragment(mainFragment.getId(),new LoginFragment());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        NewUser.setOnClickListener(v -> {
            try {
                StartFragment(mainFragment.getId(),new RegisterFragment());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return view;
    }

}