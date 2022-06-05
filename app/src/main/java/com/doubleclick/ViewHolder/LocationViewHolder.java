package com.doubleclick.ViewHolder;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;

import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 2/7/2022
 */
public class LocationViewHolder extends BaseViewHolder implements OnMapReadyCallback {
    //    private LottieAnimationView location_lotte;
    private ImageView seen;
    private OnMessageClick onMessageClick;
    private String myId;
    private TextView time;
    //    private SupportMapFragment supportMapFragment;
    private MapView map;
    private GoogleMap mGoogleMap;
    private LatLng mMapLocation;


    public LocationViewHolder(@NonNull View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
//        location_lotte = itemView.findViewById(R.id.location_lotte);
        seen = itemView.findViewById(R.id.seen);
        time = itemView.findViewById(R.id.time);
        map = itemView.findViewById(R.id.map);
        map.onCreate(null);
        map.getMapAsync(this);
//        supportMapFragment = (SupportMapFragment) ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    public void OpenLocation(Chat chat, int position) {
        time.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        List<String> list = Arrays.asList(chat.getMessage().replace("[", "").replace("]", "").replace(" ", "").split(","));
        LatLng latLng = new LatLng(Double.parseDouble(list.get(0)), Double.parseDouble(list.get(1)));
        setMapLocation(latLng);
//        MapChatFragment mapChatFragment = new MapChatFragment(latLng);
//        ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.google_map, mapChatFragment).commit();
//        OnMapReadyCallback callback = googleMap -> {
//            googleMap.addMarker(new MarkerOptions().position(latLng).title("location of you friend"));
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
//        };
//        map.getMapAsync(googleMap -> {
//            googleMap.addMarker(new MarkerOptions().position(latLng).title("location of you friend"));
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
//        });
//        supportMapFragment.getMapAsync(callback);
        if (chat.getReceiver().equals(myId)) {
            seen.setVisibility(View.INVISIBLE);
        } else {
            seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
        }
        itemView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.text_chat_option, popupMenu.getMenu());
            popupMenu.getMenu().findItem(R.id.copy).setVisible(false);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.deleteForme) {
                        onMessageClick.deleteForMe(chat, position);
                        return true;
                    } else if (item.getItemId() == R.id.deleteforeveryone) {
                        if (BaseApplication.isNetworkConnected()) {
                            onMessageClick.deleteForAll(chat, position);
                        } else {
                            Toast.makeText(itemView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    } else if (item.getItemId() == R.id.open) {
                        Intent i = new Intent();
                        try {
                            String link = "https://www.google.com/maps/?q=" + list.get(0) + "," + list.get(1);
                            i.setAction(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setPackage("com.android.chrome");
                            itemView.getContext().startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is probably not installed
                            // Try with the default browser
                            i.setPackage(null);
                            itemView.getContext().startActivity(i);
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            popupMenu.show();
        });
    }

    private void updateMapContents() {
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions().position(mMapLocation).title("location of you friend"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMapLocation, 17f));
    }

    private void setMapLocation(LatLng latLng) {
        mMapLocation = latLng;
        // If the mapView is ready, update its content.
        if (mGoogleMap != null) {
            updateMapContents();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MapsInitializer.initialize(itemView.getContext());
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        // If we have mapView data, update the mapView content.
        if (mMapLocation != null) {
            updateMapContents();
        }
    }
}
