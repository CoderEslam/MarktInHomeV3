package com.doubleclick.marktinhome.Adapters;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.OnProduct;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.viewmoretextview.ViewMoreTextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/10/2022
 */
public class ProductAdapterSmall extends RecyclerView.Adapter<ProductAdapterSmall.ProductViewHolder> {

    List<Product> products;
    private OnProduct onProduct;

    public ProductAdapterSmall(ArrayList<Product> products, OnProduct onProductItemListener) {
        this.products = products;
        this.onProduct = onProductItemListener;
    }

    @NonNull
    @Override
    public ProductAdapterSmall.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_small_product, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ProductAdapterSmall.ProductViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getProductName());
        holder.description.setText(products.get(position).getDescription());
        holder.productPrice.setText(String.format("%s", products.get(position).getPrice()));
        holder.productLastPrice.setText(String.format("%s", products.get(position).getLastPrice()));
        holder.trademark.setText(products.get(position).getTradeMark());
        holder.ratingBar.setRating(products.get(position).getRatingSeller());
        Glide.with(holder.itemView.getContext()).load(products.get(position).getOnlyImage().trim()).into(holder.imageProduct);

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
        public ImageView imageProduct;
        public ViewMoreTextView productName;
        public TextView description;
        public TextView productPrice;
        public TextView productLastPrice;
        public TextView trademark;
        private RatingBar ratingBar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
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
    }
}
