package com.doubleclick.marktinhome.Notifications;

import static com.doubleclick.marktinhome.BaseApplication.CHANNEL_ID;
import static com.doubleclick.marktinhome.BaseApplication.ShowToast;
import static com.doubleclick.marktinhome.BaseApplication.context;
import static com.doubleclick.marktinhome.BaseApplication.notificationManager;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.doubleclick.Servies.NotificationReceiver;
import com.doubleclick.marktinhome.Model.Token;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.ui.MainScreen.Chat.ChatActivity;
import com.doubleclick.marktinhome.ui.MainScreen.MainScreenActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Objects;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sent = remoteMessage.getData().get("sent");
        String user = remoteMessage.getData().get("user");

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        String currentUser = preferences.getString("currentuser", "none");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null && sent.equals(firebaseUser.getUid())) {
            if (!currentUser.equals(user)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(remoteMessage);
                } else {
                    sendNotification(remoteMessage);
                }
            }
        }
    }

    private void sendOreoNotification(RemoteMessage remoteMessage) {

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        View view = LayoutInflater.from(context).inflate(R.layout.notification_expanded, null, false);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        ImageView imageView = view.findViewById(R.id.image_view_expanded);

        Intent clickIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, 0);

        collapsedView.setTextViewText(R.id.text_view_collapsed_1, body);
        Log.e("Icon", icon);
//        Glide.with(context).load(icon).into(imageView);

        expandedView.setImageViewResource(R.id.image_view_expanded, R.drawable.icon_app);
        expandedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent);

        Notification n = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_app)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .build();

        notificationManager.notify(1, n);


        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int requestCode = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, ChatActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("userId", user);
//        intent.putExtras(bundle);
        intent.putExtra("userId", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent, defaultSound, icon);

        int id = 0;
        if (requestCode > 0) {
            id = requestCode;
        }

        oreoNotification.getManager().notify(id, builder.build());

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, ChatActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("userId", user);
//        intent.putExtras(bundle);
        intent.putExtra("userId", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0) {
            i = j;
        }

        noti.notify(i, builder.build());
    }

    private void updateToken(String refreshToken) {
        String myId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(USER).child(myId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", refreshToken);
        reference.updateChildren(map);
    }

    //  https://stackoverflow.com/questions/51123197/firebaseinstanceidservice-is-deprecated
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