package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.ChildCategory;
import com.doubleclick.marktinhome.Model.ClassificationPC;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavViewHolder> implements NavChildAdapter.onNavChild {

    private ArrayList<ClassificationPC> classificationPCS;
    private onClickChild onClickChild;

    public NavAdapter(ArrayList<ClassificationPC> classificationPCS, NavAdapter.onClickChild onClickChild) {
        this.classificationPCS = classificationPCS;
        this.onClickChild = onClickChild;
    }


    @NonNull
    @Override
    public NavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavViewHolder holder, int position) {
        holder.name.setText(classificationPCS.get(holder.getAdapterPosition()).getName());
        holder.nestedRecyclerView.setAdapter(new NavChildAdapter(classificationPCS.get(holder.getAdapterPosition()).getChildCategory(), this));
        boolean isExpandable = classificationPCS.get(holder.getAdapterPosition()).isExpendable();
        Glide.with(holder.itemView.getContext()).load(classificationPCS.get(holder.getAdapterPosition()).getImage()).into(holder.imageParent);
        holder.nestedRecyclerView.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.arrow_up);
//            holder.mArrowImage.setRotation(90);
//            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),)
        } else {
//            holder.mArrowImage.setRotation(300);
            holder.mArrowImage.setImageResource(R.drawable.arrow_down);
        }
        holder.itemView.setOnClickListener(v -> {
            onClickChild.onClickedNavParent(classificationPCS.get(position));
        });
        holder.mArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classificationPCS.get(holder.getAdapterPosition()).setExpendable(!classificationPCS.get(holder.getAdapterPosition()).isExpendable());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    public interface onClickChild {
        void onClickedNavChild(ChildCategory childCategory);

        void onClickedNavParent(ClassificationPC classificationPC);
    }

    @Override
    public int getItemCount() {
        return classificationPCS.size();
    }

    @Override
    public void onNavChildItem(ChildCategory childCategory) {
        onClickChild.onClickedNavChild(childCategory);
    }

    public class NavViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;
        public CircleImageView imageParent;

        public NavViewHolder(@NonNull View itemView) {
            super(itemView);
            imageParent = itemView.findViewById(R.id.imageParent);
            name = itemView.findViewById(R.id.name);
            mArrowImage = itemView.findViewById(R.id.arro_imageview);
            nestedRecyclerView = itemView.findViewById(R.id.list_item);
        }
    }
}
