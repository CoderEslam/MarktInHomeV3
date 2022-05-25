package com.doubleclick.DashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubleclick.ViewModel.RecentOrdersForSellerViewModel;
import com.doubleclick.marktinhome.Model.CustomerLocation;
import com.doubleclick.marktinhome.Model.RecentOrder;
import com.doubleclick.marktinhome.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private ArrayList<CustomerLocation> customerLocations = new ArrayList<>();
    private GoogleMap map;
    private RecentOrdersForSellerViewModel recentOrdersForSellerViewModel;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            for (int i = 0; i < customerLocations.size(); i++) {
                map.addMarker(new MarkerOptions().position(customerLocations.get(i).getLatLng()).title(customerLocations.get(i).getAddress()));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(customerLocations.get(i).getLatLng(), 9));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recentOrdersForSellerViewModel = new ViewModelProvider(this).get(RecentOrdersForSellerViewModel.class);
        recentOrdersForSellerViewModel.getRecentOrderLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<RecentOrder>>() {
            @Override
            public void onChanged(ArrayList<RecentOrder> recentOrderArrayList) {
                for (RecentOrder recentOrder : recentOrderArrayList) {
                    String[] latlag = recentOrder.getLocationUri().replace("[", "").replace("]", "").replace(" ", "").split(",");
                    customerLocations.add(new CustomerLocation(new LatLng(Double.parseDouble(latlag[0].trim()), Double.parseDouble(latlag[1].trim())), recentOrder.getAddress()));
                }
            }
        });
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}