package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.OnProduct;
import com.doubleclick.RecentOrderInterface;
import com.doubleclick.marktinhome.Model.RecentOrder;
import com.doubleclick.marktinhome.Model.RecentOrderData;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class RecentOrderAdapter extends RecyclerView.Adapter<RecentOrderAdapter.RecentOrderViewHolder> {
    private ArrayList<RecentOrderData> recentOrderArrayList = new ArrayList<>();

    public RecentOrderAdapter(ArrayList<RecentOrderData> recentOrderArrayList) {
        this.recentOrderArrayList = recentOrderArrayList;
    }


    @NonNull
    @Override
    public RecentOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentOrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recent_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrderViewHolder holder, int position) {

        if (recentOrderArrayList.size() != 0) {
            holder.CartName.setText(recentOrderArrayList.get(position).getProduct().getProductName());
            holder.price.setText(String.format("%s", recentOrderArrayList.get(position).getProduct().getPrice()));
            holder.quantity.setText(String.format("%s", recentOrderArrayList.get(position).getRecentOrder().getQuantity()));
            holder.imageCart.setAdapter(new ImagesAdapter(recentOrderArrayList.get(position).getProduct().getImages()));
        }

    }

    @Override
    public int getItemCount() {
        return recentOrderArrayList.size();
    }

    public class RecentOrderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView imageCart;
        private TextView CartName, price, quantity;

        public RecentOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCart = itemView.findViewById(R.id.imageCart);
            CartName = itemView.findViewById(R.id.CartName);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
