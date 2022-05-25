package com.doubleclick.marktinhome.Views.myCarousellayoutmanager;

import android.graphics.PointF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CarouselSmoothScroller {

    public CarouselSmoothScroller(@NonNull final RecyclerView.State state, final int position) {
        if (0 > position) {
            throw new IllegalArgumentException("position can't be less then 0. position is : " + position);
        }
        if (position >= state.getItemCount()) {
            throw new IllegalArgumentException("position can't be great then adapter items count. position is : " + position);
        }
    }

    @SuppressWarnings("unused")
    public PointF computeScrollVectorForPosition(final int targetPosition, @NonNull final myCarouselLayoutManager myCarouselLayoutManager) {
        return myCarouselLayoutManager.computeScrollVectorForPosition(targetPosition);
    }

    @SuppressWarnings("unused")
    public int calculateDyToMakeVisible(final View view, @NonNull final myCarouselLayoutManager myCarouselLayoutManager) {
        if (!myCarouselLayoutManager.canScrollVertically()) {
            return 0;
        }

        return myCarouselLayoutManager.getOffsetForCurrentView(view);
    }

    @SuppressWarnings("unused")
    public int calculateDxToMakeVisible(final View view, @NonNull final myCarouselLayoutManager myCarouselLayoutManager) {
        if (!myCarouselLayoutManager.canScrollHorizontally()) {
            return 0;
        }
        return myCarouselLayoutManager.getOffsetForCurrentView(view);
    }
}