package com.doubleclick.marktinhome.Adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.ChildCategory;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class NavChildAdapter extends RecyclerView.Adapter<NavChildAdapter.NavChildViewHolder> {

    private ArrayList<ChildCategory> childCategories;
    private onNavChild onNavChild;

    public NavChildAdapter(ArrayList<ChildCategory> childCategories, NavChildAdapter.onNavChild onNavChild) {
        this.childCategories = childCategories;
        this.onNavChild = onNavChild;
    }

    @NonNull
    @Override
    public NavChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavChildViewHolder holder, int position) {

        if (!TextUtils.isEmpty(childCategories.get(position).getImage()) && !TextUtils.isEmpty(childCategories.get(position).getName())) {
            Glide.with(holder.itemView.getContext()).load(childCategories.get(position).getImage()).into(holder.itemImage);
            holder.itemName.setText(childCategories.get(position).getName());
            holder.itemView.setOnClickListener(v -> {
                onNavChild.onNavChildItem(childCategories.get(position));
            });
        }

    }

    public interface onNavChild {
        void onNavChildItem(ChildCategory childCategory);
    }

    @Override
    public int getItemCount() {
        return childCategories.size();
    }

    public class NavChildViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView itemImage;
        private TextView itemName;

        public NavChildViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
        }
    }
}
