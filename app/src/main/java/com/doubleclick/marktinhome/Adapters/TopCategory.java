package com.doubleclick.marktinhome.Adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.OnItem;
import com.doubleclick.marktinhome.Model.ParentCategory;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/5/2022
 */
public class TopCategory extends RecyclerView.Adapter<TopCategory.TopViewHilder> {

    ArrayList<ParentCategory> parentCategories = new ArrayList<>();
    private OnItem onItem;

    public TopCategory(ArrayList<ParentCategory> parentCategories, OnItem onItem) {
        this.parentCategories = parentCategories;
        this.onItem = onItem;
    }

    @NonNull
    @Override
    public TopViewHilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopViewHilder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHilder holder, int position) {
        if (!TextUtils.isEmpty(parentCategories.get(position).getImage()) && !TextUtils.isEmpty(parentCategories.get(position).getName())) {
            holder.listTitle.setText(parentCategories.get(position).getName());
            Glide.with(holder.itemView.getContext()).load(parentCategories.get(position).getImage()).into(holder.circleImageView);
        }
        holder.itemView.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                onItem.onItem(parentCategories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return parentCategories.size();
    }

    public class TopViewHilder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView listTitle;

        public TopViewHilder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.selectImage);
            listTitle = itemView.findViewById(R.id.listTitle);
        }
    }
}
