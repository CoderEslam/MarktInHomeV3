package com.doubleclick.marktinhome.ui.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class ForgetFragment extends BaseFragment {


    private EditText send_email;
    private Button btn_reset;
    private FrameLayout mainFragment;
    public ForgetFragment() {
        // Required empty public constructor
    }


    public static ForgetFragment newInstance(String param1, String param2) {
        ForgetFragment fragment = new ForgetFragment();
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
        View view = inflater.inflate(R.layout.fragment_forget, container, false);
        send_email = view.findViewById(R.id.send_email);
        btn_reset = view.findViewById(R.id.btn_reset);
        mainFragment = getActivity().findViewById(R.id.mainLoginFragment);
        ResetEmail(send_email.getText().toString());
        return view;
    }

    private void ResetEmail(String email) {
        if (email.equals("")){
            ShowToast("Email is empty");
        }else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Please check you Email", Toast.LENGTH_SHORT).show();
                        try {
                            StartFragment(mainFragment.getId(),new LoginFragment());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}