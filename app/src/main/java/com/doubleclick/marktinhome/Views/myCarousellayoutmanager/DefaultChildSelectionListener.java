package com.doubleclick.marktinhome.Views.myCarousellayoutmanager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class DefaultChildSelectionListener extends CarouselChildSelectionListener {

    @NonNull
    private final OnCenterItemClickListener mOnCenterItemClickListener;

    protected DefaultChildSelectionListener(@NonNull final OnCenterItemClickListener onCenterItemClickListener, @NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager) {
        super(recyclerView, myCarouselLayoutManager);

        mOnCenterItemClickListener = onCenterItemClickListener;
    }

    @Override
    protected void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager, @NonNull final View v) {
        mOnCenterItemClickListener.onCenterItemClicked(recyclerView, myCarouselLayoutManager, v);
    }

    @Override
    protected void onBackItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager, @NonNull final View v) {
        recyclerView.smoothScrollToPosition(myCarouselLayoutManager.getPosition(v));
    }

    public static DefaultChildSelectionListener initCenterItemListener(@NonNull final OnCenterItemClickListener onCenterItemClickListener, @NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager) {
        return new DefaultChildSelectionListener(onCenterItemClickListener, recyclerView, myCarouselLayoutManager);
    }

    public interface OnCenterItemClickListener {

        void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager, @NonNull final View v);
    }
}