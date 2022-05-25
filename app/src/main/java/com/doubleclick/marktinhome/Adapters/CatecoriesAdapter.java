package com.doubleclick.marktinhome.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.CatecoriesModel;
import com.doubleclick.marktinhome.Model.ChildCategory;
import com.doubleclick.marktinhome.Model.ClassificationPC;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/3/2022
 */
public class CatecoriesAdapter extends RecyclerView.Adapter<CatecoriesAdapter.CatecoriesViewHolder> {

    private ArrayList<ClassificationPC> classificationPCS;

    public CatecoriesAdapter(ArrayList<ClassificationPC> classificationPCS) {
        this.classificationPCS = classificationPCS;
    }


    @NonNull
    @Override
    public CatecoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatecoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatecoriesViewHolder holder, int position) {
        holder.name.setText(classificationPCS.get(holder.getAdapterPosition()).getName());
        holder.nestedRecyclerView.setAdapter(new ChildAdapter(classificationPCS.get(holder.getAdapterPosition()).getChildCategory()));
        boolean isExpandable = classificationPCS.get(holder.getAdapterPosition()).isExpendable();
        Glide.with(holder.itemView.getContext()).load(classificationPCS.get(holder.getAdapterPosition()).getImage()).into(holder.imageParent);
        holder.nestedRecyclerView.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.arrow_up);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.arrow_down);
        }
        holder.mArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classificationPCS.get(holder.getAdapterPosition()).setExpendable(!classificationPCS.get(holder.getAdapterPosition()).isExpendable());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return classificationPCS.size();
    }

    public class CatecoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;
        public CircleImageView imageParent;

        public CatecoriesViewHolder(@NonNull View itemView) {

            super(itemView);
            imageParent = itemView.findViewById(R.id.imageParent);
            name = itemView.findViewById(R.id.name);
            mArrowImage = itemView.findViewById(R.id.arro_imageview);
            nestedRecyclerView = itemView.findViewById(R.id.list_item);
        }
    }
}
