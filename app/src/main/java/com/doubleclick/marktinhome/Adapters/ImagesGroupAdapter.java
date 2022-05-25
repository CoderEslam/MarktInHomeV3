package com.doubleclick.marktinhome.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.PhotoView.PhotoView;
import com.doubleclick.marktinhome.Views.carouselrecyclerviewReflaction.view.ReflectionImageView;
import com.doubleclick.marktinhome.ui.MainScreen.Groups.ViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 4/20/2022
 */
public class ImagesGroupAdapter extends RecyclerView.Adapter<ImagesGroupAdapter.ImagesGroupViewHolder> {

    private List<String> images = new ArrayList<>();

    private ArrayList<Uri> uris = new ArrayList<>();
    private String type = "";


    public ImagesGroupAdapter(List<String> images) {
        this.images = images;
    }

    public ImagesGroupAdapter(ArrayList<Uri> uris, String type) {
        this.uris = uris;
        this.type = type;
    }

    @NonNull
    @Override
    public ImagesGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagesGroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_images, parent, false));
    }

    @Override
    public int getItemCount() {
        if (images.size() != 0) {
            return images.size();
        } else if (uris.size() != 0) {
            return uris.size();
        } else {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ImagesGroupViewHolder holder, int position) {
        if (type.equals("uri")) {
            Log.e("clipDataII", uris.toString());
            holder.image.setImageURI(uris.get(holder.getAdapterPosition()));
        } else {
            Glide.with(holder.itemView.getContext()).load(images.get(holder.getAdapterPosition())).into(holder.image);
        }

        holder.image.setOnClickListener(v -> {
            Intent intent =  new Intent(holder.itemView.getContext(), ViewActivity.class);
            intent.putExtra("url",images.get(holder.getAdapterPosition()));
            intent.putExtra("type","image");
            holder.itemView.getContext().startActivity(intent);
        });

    }

    public class ImagesGroupViewHolder extends RecyclerView.ViewHolder {
        private ReflectionImageView image;

        public ImagesGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
