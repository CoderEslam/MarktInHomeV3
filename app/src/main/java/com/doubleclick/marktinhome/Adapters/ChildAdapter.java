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

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/3/2022
 */
public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    private ArrayList<ChildCategory> childCategories = new ArrayList<>();

    private OnChild onChild;

    public ChildAdapter(ArrayList<ChildCategory> childCategories) {
        this.childCategories = childCategories;
    }

    public ChildAdapter(ArrayList<ChildCategory> childCategories, OnChild onChild) {
        this.childCategories = childCategories;
        this.onChild = onChild;
    }

    @NonNull
    @Override
    public ChildAdapter.ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ChildViewHolder holder, int position) {

        if (!TextUtils.isEmpty(childCategories.get(holder.getAdapterPosition()).getImage()) && !TextUtils.isEmpty(childCategories.get(holder.getAdapterPosition()).getName())) {
            Glide.with(holder.itemView.getContext()).load(childCategories.get(holder.getAdapterPosition()).getImage()).into(holder.itemImage);
            holder.itemName.setText(childCategories.get(holder.getAdapterPosition()).getName());
            holder.itemView.setOnClickListener(v -> {
                onChild.onChild(childCategories.get(holder.getAdapterPosition()));
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onChild.onChildLongClickListner(childCategories.get(holder.getAdapterPosition()));
                    return true;
                }
            });
        }

    }

    public interface OnChild {
        void onChild(ChildCategory childCategory);

        void onChildLongClickListner(ChildCategory childCategory);
    }

    @Override
    public int getItemCount() {
        return childCategories.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView itemImage;
        private TextView itemName;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);

        }
    }
}
