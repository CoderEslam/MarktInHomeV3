package com.doubleclick.marktinhome;

import android.Manifest;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.multidex.MultiDex;

import com.doubleclick.downloader.PRDownloader;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class BaseApplication extends Application {

    public static Context context;
    public static final String CHANNEL_ID = "marktinhome";
    public static NotificationManagerCompat notificationManager;
    public static String HTMLText = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        PRDownloader.initialize(getApplicationContext());
        EmojiManager.install(new GoogleEmojiProvider());
        Realm.init(this); // context, usually an Activity or Application
        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getInstance(config);


        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
        MultiDex.install(this);
        Dexter.withContext(this).withPermissions(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).check();
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Example Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void ShowToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
