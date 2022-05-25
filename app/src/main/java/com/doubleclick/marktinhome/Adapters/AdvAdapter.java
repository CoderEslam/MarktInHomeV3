package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.AdvInterface;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class AdvAdapter extends RecyclerView.Adapter<AdvAdapter.AdvViewHolder> {

    private ArrayList<Advertisement> advertisements = new ArrayList<>();
    private AdvInterface advInterface;

    public AdvAdapter(ArrayList<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public AdvAdapter(ArrayList<Advertisement> advertisements, AdvInterface advInterface) {
        this.advertisements = advertisements;
        this.advInterface = advInterface;
    }

    @NonNull
    @Override
    public AdvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdvViewHolder holder, int position) {
        if (advertisements.size() != 0) {
            Glide.with(holder.itemView.getContext()).load(advertisements.get(holder.getAdapterPosition()).getImage()).into(holder.imageAdv);
            holder.description.setText(advertisements.get(holder.getAdapterPosition()).getText());
            holder.option.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.option_tradmark, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.delete) {
                            advInterface.OnDeleteAdvertisement(advertisements.get(holder.getAdapterPosition()));
                        }
                        if (id == R.id.edit) {
                            advInterface.OnEditAdvertisement(advertisements.get(holder.getAdapterPosition()));
                        }
                        return true;
                    }
                });
                popupMenu.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return advertisements.size();
    }

    public class AdvViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageAdv;
        private TextView description;
        private ImageView option;

        public AdvViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAdv = itemView.findViewById(R.id.imageAdv);
            description = itemView.findViewById(R.id.description);
            option = itemView.findViewById(R.id.option);
        }
    }
}
