package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 6/24/2022
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    private List<String> images = new ArrayList<>();

    public ImagesAdapter(String images) {
        List<String> i = Arrays.asList(images.replace("[", "").replace("]", "").replace(" ", "").split(","));
        this.images = i;
    }

    @NonNull
    @Override
    public ImagesAdapter.ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.images, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ImagesViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(images.get(holder.getBindingAdapterPosition())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView image, delete;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
            delete.setVisibility(View.GONE);
        }
    }
}
