package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.OnProduct;
import com.doubleclick.marktinhome.Model.CategoricalProduct;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 5/30/2022
 */
public class CategoricalAdapter extends RecyclerView.Adapter<CategoricalAdapter.CategoricalViewHolder> {

    private ArrayList<CategoricalProduct> categoricalProducts;
    private OnProduct onProduct;

    public CategoricalAdapter(ArrayList<CategoricalProduct> categoricalProducts, OnProduct onProduct) {
        this.categoricalProducts = categoricalProducts;
        this.onProduct = onProduct;
    }


    @NonNull
    @Override
    public CategoricalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoricalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorical_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoricalViewHolder holder, int position) {

        holder.name.setText(categoricalProducts.get(position).getName());
        holder.categoricals.setAdapter(new ProductAdapter(categoricalProducts.get(position).getProduct(), onProduct));
        holder.viewmore.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(), "Load more", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return categoricalProducts.size();
    }

    public class CategoricalViewHolder extends RecyclerView.ViewHolder {
        private SocialTextView name;
        private TextView viewmore;
        private RecyclerView categoricals;

        public CategoricalViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            viewmore = itemView.findViewById(R.id.viewmore);
            categoricals = itemView.findViewById(R.id.categoricals);
        }
    }
}
