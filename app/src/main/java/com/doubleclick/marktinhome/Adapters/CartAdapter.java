package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.BaseApplication.ShowToast;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.OnCartLisnter;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/2/2022
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Cart> carts = new ArrayList<>();
    private OnCartLisnter onCartLisnter;
    private DatabaseReference reference;

    public CartAdapter(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    public CartAdapter(ArrayList<Cart> carts, DatabaseReference reference, OnCartLisnter onCartLisnter) {
        this.carts = carts;
        this.reference = reference;
        this.onCartLisnter = onCartLisnter;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layaut_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        if (carts.size() != 0) {
            holder.CartName.setText(carts.get(position).getProductName());
            holder.price.setText(String.format("%s", carts.get(position).getPrice()));
            holder.quantity.setText(String.format("%s", carts.get(position).getQuantity()));
            Glide.with(holder.itemView.getContext()).load(carts.get(position).getImages()).into(holder.imageCart);

            holder.add.setOnClickListener(v -> {
                onCartLisnter.OnAddItemOrder(carts.get(position), position);
            });
            holder.mins.setOnClickListener(v -> {
                if (carts.get(position).getQuantity() <= 1) {
                    ShowToast("you can't order less than one!");
                    return;
                } else {
                    onCartLisnter.OnMinsItemOrder(carts.get(position), position);
                }
            });

            holder.delete.setOnClickListener(v -> {
                try {
                    onCartLisnter.OnDeleteItemOrder(carts.get(position), position);
                    holder.itemView.setVisibility(View.GONE);
                    notifyItemRemoved(holder.getAdapterPosition());
                } catch (IndexOutOfBoundsException e) {
                    Log.e("CartAdapterException", e.getMessage());
                }

            });

        }


    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageCart;
        private TextView CartName, price, quantity;
        private ImageView add, mins, delete;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCart = itemView.findViewById(R.id.imageCart);
            CartName = itemView.findViewById(R.id.CartName);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            add = itemView.findViewById(R.id.add);
            mins = itemView.findViewById(R.id.mins);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
