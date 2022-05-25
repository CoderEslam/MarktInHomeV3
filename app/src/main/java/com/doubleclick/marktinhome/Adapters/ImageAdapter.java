package com.doubleclick.marktinhome.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/14/2022
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    ArrayList<String> uris ;
    deleteImage deleteImage;

    public ImageAdapter(ArrayList<String> uris) {
        this.uris = uris;
    }

    public ImageAdapter(ArrayList<String> uris, ImageAdapter.deleteImage deleteImage) {
        this.uris = uris;
        this.deleteImage = deleteImage;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.images, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        holder.image.setImageURI(Uri.parse(uris.get(position)));
        Glide.with(holder.itemView.getContext()).load(Uri.parse(uris.get(position))).into(holder.image);
        holder.delete.setOnClickListener(v -> {
            deleteImage.deleteImage(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image, delete;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public interface deleteImage {
        void deleteImage(int postion);
    }
}
