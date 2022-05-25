package com.doubleclick.marktinhome.Views.customnavigationdrawer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.customnavigationdrawer.data.MenuItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/4/2022
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    List<MenuItem> menuItems = new ArrayList<>();

    public RecyclerAdapter(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.titleTV1.setText(menuItems.get(position).getTitle());
        holder.backgroundIV.setImageResource(menuItems.get(position).getImageId());
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(), "GGGGGGGG", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView backgroundIV;
        private TextView titleTV1;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundIV = itemView.findViewById(R.id.backgroundIV);
            titleTV1 = itemView.findViewById(R.id.titleTV1);
        }
    }
}
