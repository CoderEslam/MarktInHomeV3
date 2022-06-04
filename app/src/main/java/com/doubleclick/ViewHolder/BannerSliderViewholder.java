package com.doubleclick.ViewHolder;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.doubleclick.marktinhome.Adapters.SliderAdapter;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.imageslider.ImageSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created By Eslam Ghazy on 3/9/2022
 */
public class BannerSliderViewholder extends RecyclerView.ViewHolder {
    private ViewPager bannerSliderViewPager;
    private int currentPage;
    private Timer timer;
    private final long DELAY_TIME = 2000;
    private final long PERIOD_TIME = 2000;

    public BannerSliderViewholder(@NonNull View itemView) {
        super(itemView);
        bannerSliderViewPager = itemView.findViewById(R.id.banner_slier_view_pager);

    }

    public void setBannerSliderViewPager(ArrayList<Advertisement> advertisements) {
        currentPage = 2;
        //////////////////////////////////////////////////////////////////
        SliderAdapter sliderAdapter = new SliderAdapter(advertisements);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        bannerSliderViewPager.setCurrentItem(currentPage);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //     onPageChangeListener
        bannerSliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { //is not important
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                        PageLooper(sliderModelList);  // is not important
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        StartbannerSlideShow(advertisements);
        //if banner Touch this mathod is excut
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StopBannerSlideShow();
                //
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    StartbannerSlideShow(advertisements);
                }
                return false;
            }
        });
    }


    // this resbonsable to loop slider
    private void StartbannerSlideShow(final List<Advertisement> advertisementList) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= advertisementList.size()) {
                    currentPage = 0;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    private void StopBannerSlideShow() {
        timer.cancel();
    }
}
