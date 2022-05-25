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
 * Created By Eslam Ghazy on 3/3/2022
 */
public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {

    private ArrayList<ParentCategory> parentCategories = new ArrayList<>();
    private OnItem onItem;


    public ParentAdapter(ArrayList<ParentCategory> parentCategories, OnItem onItem) {
        this.parentCategories = parentCategories;
        this.onItem = onItem;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ParentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        if (!TextUtils.isEmpty(parentCategories.get(holder.getAdapterPosition()).getImage()) && !TextUtils.isEmpty(parentCategories.get(holder.getAdapterPosition()).getName())) {
            holder.listTitle.setText(parentCategories.get(holder.getAdapterPosition()).getName());
            Glide.with(holder.itemView.getContext()).load(parentCategories.get(position).getImage()).into(holder.circleImageView);
            holder.order.setText(String.format("%d", -1 * Integer.parseInt(parentCategories.get(holder.getAdapterPosition()).getOrder())));
        }
        holder.itemView.setOnClickListener(v -> {
            if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                onItem.onItem(parentCategories.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItem.onItemLong(parentCategories.get(holder.getAdapterPosition()));
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return parentCategories.size();
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView listTitle, order;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.selectImage);
            listTitle = itemView.findViewById(R.id.listTitle);
            order = itemView.findViewById(R.id.order);
        }
    }


}
