package com.doubleclick.marktinhome.Views.myCarousellayoutmanager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class CenterScrollListener extends RecyclerView.OnScrollListener {

    private boolean mAutoSet = true;

    @Override
    public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof myCarouselLayoutManager)) {
            mAutoSet = true;
            return;
        }

        final myCarouselLayoutManager lm = (myCarouselLayoutManager) layoutManager;
        if (!mAutoSet) {
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                final int scrollNeeded = lm.getOffsetCenterView();
                if (myCarouselLayoutManager.HORIZONTAL == lm.getOrientation()) {
                    recyclerView.smoothScrollBy(scrollNeeded, 0);
                } else {
                    recyclerView.smoothScrollBy(0, scrollNeeded);
                }
                mAutoSet = true;
            }
        }
        if (RecyclerView.SCROLL_STATE_DRAGGING == newState || RecyclerView.SCROLL_STATE_SETTLING == newState) {
            mAutoSet = false;
        }
    }
}