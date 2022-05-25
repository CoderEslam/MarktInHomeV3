package com.doubleclick.marktinhome.ui.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.ui.MainScreen.MainScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class LoginFragment extends BaseFragment {

    private AppCompatButton login, signup;
    private FrameLayout mainFragment;
    private TextInputEditText email, password;
    private LottieAnimationView loadingAnimView;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        login = view.findViewById(R.id.login);
        signup = view.findViewById(R.id.signup);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        loadingAnimView = view.findViewById(R.id.loadingAnimView);
        mainFragment = getActivity().findViewById(R.id.mainLoginFragment);

        signup.setOnClickListener(v -> {
            try {
                StartFragment(mainFragment.getId(), new RegisterFragment());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        login.setOnClickListener(v -> {
            LogIn(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(password.getText()).toString());
            loadingAnimView.setVisibility(View.VISIBLE);
        });
        return view;
    }

    private void LogIn(String email, String password) {
        if (isAllInserted())
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getContext(),MainScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    requireActivity().finish();
                    startActivity(intent);
                    loadingAnimView.setVisibility(View.GONE);
                } else {
                    ShowToast( Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });

    }

    private boolean isAllInserted() {
        if (password.getText().toString().equals("")) {
            ShowToast( "insert password");
            return false;
        }
        if (email.getText().toString().equals("")) {
            ShowToast( "insert email");
            return false;
        }
        return true;
    }


}