package com.doubleclick.marktinhome.Views.myCarousellayoutmanager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class CarouselChildSelectionListener {

    @NonNull
    private final RecyclerView mRecyclerView;
    @NonNull
    private final myCarouselLayoutManager mMyCarouselLayoutManager;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            final RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
            final int position = holder.getAdapterPosition();

            if (position == mMyCarouselLayoutManager.getCenterItemPosition()) {
                onCenterItemClicked(mRecyclerView, mMyCarouselLayoutManager, v);
            } else {
                onBackItemClicked(mRecyclerView, mMyCarouselLayoutManager, v);
            }
        }
    };

    protected CarouselChildSelectionListener(@NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager) {
        mRecyclerView = recyclerView;
        mMyCarouselLayoutManager = myCarouselLayoutManager;

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull final View view) {
                view.setOnClickListener(mOnClickListener);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull final View view) {
                view.setOnClickListener(null);
            }
        });
    }

    protected abstract void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager, @NonNull final View v);

    protected abstract void onBackItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final myCarouselLayoutManager myCarouselLayoutManager, @NonNull final View v);
}