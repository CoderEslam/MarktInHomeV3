package com.doubleclick.ViewHolder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.doubleclick.marktinhome.R;

/**
 * Created By Eslam Ghazy on 2/5/2022
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public LottieAnimationView animationView;
    public BaseViewHolder(View itemView) {
        super(itemView);
            animationView = itemView.findViewById(R.id.animationView);
    }

}
