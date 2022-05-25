package com.doubleclick.marktinhome.Adapters;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.PhotoView.PhotoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/15/2022
 */
public class ProductSliderAdapter extends PagerAdapter {

    private List<String> list = new ArrayList<>();

    public ProductSliderAdapter(String list) {
        try {
            this.list = Arrays.asList(list.trim().replace("[", "").replace("]", "").replace(" ", "").split(","));
        } catch (NullPointerException e) {

        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_image_layout, container, false);
        PhotoView banner = view.findViewById(R.id.banner_sliderImageView);
        Glide.with(container.getContext()).load(list.get(position).trim().toString()).into(banner);
        container.addView(view, 0);
        return view;
    }
}
