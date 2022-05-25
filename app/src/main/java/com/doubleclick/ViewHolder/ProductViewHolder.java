package com.doubleclick.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.OnProduct;
import com.doubleclick.marktinhome.Adapters.ProductAdapter;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {


    private RecyclerView ProductRecycler;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        ProductRecycler = itemView.findViewById(R.id.ProductRecycler);

    }

    public void setProduct(ArrayList<Product> products, OnProduct onProduct) {
        ProductAdapter productAdapter = new ProductAdapter(products, onProduct);
        ProductRecycler.setAdapter(productAdapter);
    }

}
