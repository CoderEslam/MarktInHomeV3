package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.CART;
import static com.doubleclick.marktinhome.Model.Constantes.RECENTORDER;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.OnOrder;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Model.Orders;
import com.doubleclick.marktinhome.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/7/2022
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Orders> orders = new ArrayList<>();
    private OnOrder onOrder;


    public OrderAdapter(ArrayList<Orders> orders, OnOrder onOrder) {
        this.orders = orders;
        this.onOrder = onOrder;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        holder.nameOrder.setText(orders.get(holder.getAdapterPosition()).getProductName());
        holder.PriceOrder.setText(String.format("%s", orders.get(holder.getAdapterPosition()).getPrice()));
        holder.quantityOrder.setText(String.format("%s", orders.get(holder.getAdapterPosition()).getQuantity()));
        holder.totalPrice.setText(String.format("%s", orders.get(holder.getAdapterPosition()).getTotalPrice()));
        holder.custommerName.setText(orders.get(position).getName());
        holder.custommerPhone.setText(orders.get(position).getPhone());
        holder.AnothercustommerPhone.setText(orders.get(position).getAnotherPhone());
        holder.custommerAddress.setText(orders.get(position).getAddress());
        holder.CustomerLocation.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(orders.get(position).getLocationUri())) {
                try {
                    List<String> uri = Arrays.asList(orders.get(position).getLocationUri().replace("[", "").replace("]", "").replace(" ", "").trim().split(","));
                    // https://developer.android.com/guide/components/intents-common#ViewMap
                    Uri i = Uri.parse("geo:0,0?q=" + uri.get(0) + "," + uri.get(1));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, i);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(holder.itemView.getContext().getPackageManager()) != null) {
                        holder.itemView.getContext().startActivity(mapIntent);
                    }
                } catch (Exception e) {
                    Toast.makeText(holder.itemView.getContext(), "there is no location", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Glide.with(holder.itemView.getContext()).load(orders.get(holder.getAdapterPosition()).getOnlyImage()).into(holder.orderImage);
        holder.ok.setOnClickListener(v -> {
            onOrder.OnOKItemOrder(orders.get(holder.getAdapterPosition()));
            holder.itemView.setVisibility(View.GONE);
            orders.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

        holder.cancel.setOnClickListener(v -> {
            onOrder.OnCancelItemOrder(orders.get(holder.getAdapterPosition()));
            holder.itemView.setVisibility(View.GONE);
            orders.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView orderImage;
        private TextView nameOrder, PriceOrder, cancel, ok, quantityOrder, totalPrice, custommerName, custommerPhone, AnothercustommerPhone, custommerAddress;
        private Button CustomerLocation;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOrder = itemView.findViewById(R.id.nameOrder);
            orderImage = itemView.findViewById(R.id.orderImage);
            PriceOrder = itemView.findViewById(R.id.PriceOrder);
            quantityOrder = itemView.findViewById(R.id.quantityOrder);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            cancel = itemView.findViewById(R.id.cancel);
            ok = itemView.findViewById(R.id.ok);
            custommerName = itemView.findViewById(R.id.custommerName);
            custommerPhone = itemView.findViewById(R.id.custommerPhone);
            AnothercustommerPhone = itemView.findViewById(R.id.AnothercustommerPhone);
            custommerAddress = itemView.findViewById(R.id.custommerAddress);
            CustomerLocation = itemView.findViewById(R.id.CustomerLocation);

        }

    }
}
