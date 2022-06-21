package com.doubleclick.marktinhome.Notifications;

import static com.doubleclick.marktinhome.Model.Constantes.USER;

import androidx.annotation.NonNull;

import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//    implementation 'com.google.firebase:firebase-messaging:20.2.4' // implimt this

//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;
import java.util.Objects;


public class MyFirebaseIdService extends FirebaseMessagingService {

    private void updateToken(String refreshToken) {
        String myId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USER).child(myId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", refreshToken);
        reference.updateChildren(map);
    }

    //https://stackoverflow.com/questions/51123197/firebaseinstanceidservice-is-deprecated
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if (firebaseUser != null) {
            updateToken(s);
        }
    }
}