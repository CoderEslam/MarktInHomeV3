package com.doubleclick.marktinhome.ui.Login;

import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.ui.MainScreen.MainScreenActivity;
import com.doubleclick.marktinhome.ui.Splash.Splash4Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BaseFragment {


    private FrameLayout mainFragment;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private TextView ihaveaccount;
    private TextInputEditText edt_user_name, email, password;
    private AppCompatButton login;
    private LottieAnimationView loadingAnimView;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mainFragment = getActivity().findViewById(R.id.mainLoginFragment);
        ihaveaccount = view.findViewById(R.id.ihaveaccount);
        edt_user_name = view.findViewById(R.id.edt_user_name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        loadingAnimView = view.findViewById(R.id.loadingAnimView);

        ihaveaccount.setOnClickListener(v -> {
            try {
                StartFragment(mainFragment.getId(), new LoginFragment());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        view.findViewById(R.id.google).setOnClickListener(v -> {
            signIn();
        });
        login.setOnClickListener(v -> {
            SignInEmail(edt_user_name.getText().toString(), email.getText().toString(), password.getText().toString());
            loadingAnimView.setVisibility(View.VISIBLE);
        });

        createRequest();

        return view;
    }

    private void SignInEmail(String username, String email, String password) {
        if (isAllInserted()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        myId = mAuth.getCurrentUser().getUid().toString();
                        HashMap<String, Object> user = new HashMap<>();
                        user.put("name", username);
                        user.put("email", email);
                        user.put("address", "");
                        user.put("id", myId);
                        user.put("password", password);
                        user.put("image", "");
                        user.put("phone", "");
                        user.put("date", new Date().getTime());
                        user.put("status", "offline");
                        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                user.put("token", task.getResult());
                                reference.child(USER).child(myId).setValue(user);
                                loadingAnimView.setVisibility(View.GONE);
                                Intent intent = new Intent(getContext(), MainScreenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                requireActivity().finish();
                                startActivity(intent);

                            }
                        });
                    } else {
                        ShowToast("you don't have internet connection");
                    }
                }
            });

        }
    }

    private boolean isAllInserted() {
        if (edt_user_name.getText().toString().equals("")) {
            ShowToast("insert name");
            return false;
        }
        if (password.getText().toString().equals("")) {
            ShowToast("insert password");
            return false;
        }
        if (email.getText().toString().equals("")) {
            ShowToast("insert email");
            return false;
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getContext(), "ApiException at = " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getContext(), "Dooooooooone", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Exception at = " + task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


    }
}