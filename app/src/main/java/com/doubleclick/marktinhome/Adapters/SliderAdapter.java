package com.doubleclick.marktinhome.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.PhotoView.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private ArrayList<Advertisement> advertisements = new ArrayList<>();

    public SliderAdapter(ArrayList<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @Override
    public int getCount() {
        return advertisements.size();
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
        TextView text = view.findViewById(R.id.text);
        Glide.with(view).load(advertisements.get(position).getImage()).into(banner);
        text.setText(advertisements.get(position).getText());
        container.addView(view, 0);
        return view;
    }
}
