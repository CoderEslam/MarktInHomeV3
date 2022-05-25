package com.doubleclick.marktinhome.Views.myCarousellayoutmanager;

import android.view.View;

import androidx.annotation.NonNull;


public class CarouselZoomPostLayoutListener extends myCarouselLayoutManager.PostLayoutListener {

    private final float mScaleMultiplier;

    public CarouselZoomPostLayoutListener() {
        this(0.17f);
    }

    public CarouselZoomPostLayoutListener(final float scaleMultiplier) {
        mScaleMultiplier = scaleMultiplier;
    }

    @Override
    public ItemTransformation transformChild(@NonNull final View child, final float itemPositionToCenterDiff, final int orientation) {
        final float scale = 1.0f - mScaleMultiplier * Math.abs(itemPositionToCenterDiff);

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (myCarouselLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) / 2f;
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
            translateX = 0;
        } else {
            final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) / 2f;
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;
            translateY = 0;
        }

        return new ItemTransformation(scale, scale, translateX, translateY);
    }
}