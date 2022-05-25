package com.doubleclick.marktinhome.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created By Eslam Ghazy on 3/24/2022
 */
public class CustomerLocation {

    private LatLng latLng;
    private String address;

    public CustomerLocation(LatLng latLng, String address) {
        this.latLng = latLng;
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
