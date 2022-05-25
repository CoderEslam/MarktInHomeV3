package com.doubleclick.marktinhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.widget.Toast;

import com.doubleclick.marktinhome.Adapters.ViewPagerAdapter;
import com.doubleclick.marktinhome.Views.liquid_swipe.LiquidPager;
import com.doubleclick.marktinhome.ui.MainScreen.MainScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.util.FileUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LiquidPager pager;
    private ViewPagerAdapter viewPager;
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener firebaseAuthListener;
    String ShareUrl = "NoUrl";

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.pager);
        viewPager = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(viewPager);
        mAuth = FirebaseAuth.getInstance();
        Share();
        firebaseAuthListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(this, MainScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
        };



    }

    private void Share() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> parms = uri.getPathSegments();
            /*
             *to get last part of url
             */
            ShareUrl = parms.get(parms.size() - 1);
            Intent intent = new Intent(this, MainScreenActivity.class);
            if (!ShareUrl.equals("NoUrl")) {
                intent.putExtra("ShareUrl", ShareUrl);
                intent.putExtra("type", parms.get(parms.size() - 2));
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}