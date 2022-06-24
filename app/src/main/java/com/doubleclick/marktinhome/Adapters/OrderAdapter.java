package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.CART;
import static com.doubleclick.marktinhome.Model.Constantes.RECENTORDER;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.OnOrder;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Model.Orders;
import com.doubleclick.marktinhome.Model.OrdersDate;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.viewmoretextview.ViewMoreTextView;
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

    private ArrayList<OrdersDate> orders = new ArrayList<>();
    private OnOrder onOrder;


    public OrderAdapter(ArrayList<OrdersDate> orders, OnOrder onOrder) {
        this.orders = orders;
        this.onOrder = onOrder;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        holder.nameOrder.setText(orders.get(holder.getBindingAdapterPosition()).getProduct().getProductName());
        holder.PriceOrder.setText(String.format("Price\n%s", orders.get(holder.getBindingAdapterPosition()).getProduct().getPrice()));
        holder.quantityOrder.setText(String.format("Quantity\n%s", orders.get(holder.getBindingAdapterPosition()).getOrders().getQuantity()));
        holder.totalPrice.setText(String.format("total price\n%s", orders.get(holder.getBindingAdapterPosition()).getOrders().getQuantity() * orders.get(holder.getBindingAdapterPosition()).getProduct().getPrice()));
        holder.custommerName.setText(orders.get(position).getOrders().getName());
        holder.custommerPhone.setText(orders.get(position).getOrders().getPhone());
        holder.AnothercustommerPhone.setText(orders.get(position).getOrders().getAnotherPhone());
        holder.custommerAddress.setText(orders.get(position).getOrders().getAddress());
        holder.CustomerLocation.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(orders.get(position).getOrders().getLocationUri())) {
                try {
                    List<String> uri = Arrays.asList(orders.get(position).getOrders().getLocationUri().replace("[", "").replace("]", "").replace(" ", "").trim().split(","));
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
        holder.orderImage.setAdapter(new ImagesAdapter(orders.get(holder.getBindingAdapterPosition()).getProduct().getImages()));
        holder.color.setText(orders.get(holder.getBindingAdapterPosition()).getOrders().getToggleItemColor());
        holder.size.setText(orders.get(holder.getBindingAdapterPosition()).getOrders().getToggleItemSize());
        holder.ok.setOnClickListener(v -> {
            onOrder.OnOKItemOrder(orders.get(holder.getBindingAdapterPosition()));
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
        holder.nameOrder.setOnClickListener(v -> {
            holder.nameOrder.toggle();
        });
        holder.custommerAddress.setOnClickListener(v -> {
            holder.custommerAddress.toggle();
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView orderImage;
        private ViewMoreTextView nameOrder, custommerAddress;
        private TextView PriceOrder, cancel, ok, quantityOrder, totalPrice, custommerName, custommerPhone, AnothercustommerPhone, color, size;
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
            color = itemView.findViewById(R.id.color);
            size = itemView.findViewById(R.id.size);
            custommerName = itemView.findViewById(R.id.custommerName);
            custommerPhone = itemView.findViewById(R.id.custommerPhone);
            AnothercustommerPhone = itemView.findViewById(R.id.AnothercustommerPhone);
            custommerAddress = itemView.findViewById(R.id.custommerAddress);
            CustomerLocation = itemView.findViewById(R.id.CustomerLocation);
            nameOrder.setAnimationDuration(500)
                    .setEllipsizedText("View More")
                    .setVisibleLines(1)
                    .setIsExpanded(false)
                    .setEllipsizedTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blueDark));
            custommerAddress.setAnimationDuration(500)
                    .setEllipsizedText("View More")
                    .setVisibleLines(1)
                    .setIsExpanded(false)
                    .setEllipsizedTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blueDark));
        }

    }
}
