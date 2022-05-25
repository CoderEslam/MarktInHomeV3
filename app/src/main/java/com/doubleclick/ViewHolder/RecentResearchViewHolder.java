package com.doubleclick.ViewHolder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.OnProduct;
import com.doubleclick.ViewMore;
import com.doubleclick.marktinhome.Adapters.ProductAdapterSmall;
import com.doubleclick.marktinhome.Adapters.RecentSearchAdapter;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/11/2022
 */
public class RecentResearchViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recentSearch;
    private TextView viewmore;

    public RecentResearchViewHolder(@NonNull View itemView) {
        super(itemView);
        recentSearch = itemView.findViewById(R.id.recentSearch);
        viewmore = itemView.findViewById(R.id.viewmore);
        ViewCompat.setNestedScrollingEnabled(recentSearch, true);

    }

    public void setRecentSearch(ArrayList<Product> products, OnProduct onProduct, ViewMore viewMore) {
        RecentSearchAdapter gridViewAdapter = new RecentSearchAdapter(products, onProduct);
        recentSearch.setAdapter(gridViewAdapter);
        viewmore.setOnClickListener(v -> {
            viewMore.getViewMore(products);
        });
    }
}
