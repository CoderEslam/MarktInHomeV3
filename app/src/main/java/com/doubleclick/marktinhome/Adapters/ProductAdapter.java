package com.doubleclick.marktinhome.Adapters;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.doubleclick.OnProduct;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.viewmoretextview.ViewMoreTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created By Eslam Ghazy on 3/9/2022
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> products = new ArrayList<>();
    private OnProduct onProduct;

    public ProductAdapter(ArrayList<Product> products, OnProduct onProductItemListener) {
        this.products = products;
        this.onProduct = onProductItemListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_with_view_pager, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getProductName());
        holder.description.setText(products.get(position).getDescription());
        holder.productPrice.setText(String.format("%s", products.get(position).getPrice()));
        holder.productLastPrice.setText(String.format("%s", products.get(position).getLastPrice()));
        holder.trademark.setText(products.get(position).getTradeMark());
        holder.ratingBar.setRating(Float.parseFloat(products.get(position).getTotalPercentage()));
        String list = products.get(position).getImages();
        holder.setBannerSliderViewPager(list);
        holder.itemView.setOnClickListener(v -> {
            onProduct.onItemProduct(products.get(position));
        });

        holder.productName.setOnClickListener(v -> {
            holder.productName.toggle();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ViewPager banner_slier_view_pager;
        public ViewMoreTextView productName;
        public TextView description;
        public TextView productPrice;
        public TextView productLastPrice;
        public TextView trademark;
        private int currentPage;
        private RatingBar ratingBar;
        private Timer timer;
        private final long DELAY_TIME = 2000;
        private final long PERIOD_TIME = 2000;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            banner_slier_view_pager = itemView.findViewById(R.id.banner_slier_view_pager);
            productName = itemView.findViewById(R.id.productName);
            description = itemView.findViewById(R.id.description);
            productPrice = itemView.findViewById(R.id.productPrice);
            productLastPrice = itemView.findViewById(R.id.productLastPrice);
            trademark = itemView.findViewById(R.id.trademark);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            productName.setAnimationDuration(500)
                    .setEllipsizedText("View More")
                    .setVisibleLines(2)
                    .setIsExpanded(false)
                    .setEllipsizedTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blueDark));
        }

        @SuppressLint("ClickableViewAccessibility")
        public void setBannerSliderViewPager(String list) {
            currentPage = 2;
            //////////////////////////////////////////////////////////////////
            ProductSliderAdapter sliderAdapter = new ProductSliderAdapter(list);
            banner_slier_view_pager.setAdapter(sliderAdapter);
            banner_slier_view_pager.setClipToPadding(false);
            banner_slier_view_pager.setPageMargin(20);
            banner_slier_view_pager.setCurrentItem(currentPage);
            try {
                List<String> l = Arrays.asList(list.trim().replace("[", "").replace("]", "").replace(" ", "").split(","));
                StartbannerSlideShow(l);
                banner_slier_view_pager.setOnTouchListener((View v, MotionEvent event) -> {
                    StopBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        StartbannerSlideShow(l);
                    }
                    return false;
                });
            } catch (NullPointerException e) {

            }
            //if banner Touch this mathod is excut

        }

        // this resbonsable to loop slider
        private void StartbannerSlideShow(final List<String> list) {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= list.size()) {
                        currentPage = 0;
                    }
                    banner_slier_view_pager.setCurrentItem(currentPage++);
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
}
