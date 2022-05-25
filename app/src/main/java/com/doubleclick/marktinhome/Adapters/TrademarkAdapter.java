package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.TRADEMARK;

import android.util.Log;
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
import com.doubleclick.Tradmarkinterface;
import com.doubleclick.ViewHolder.TrademarkViewHolder;
import com.doubleclick.marktinhome.Model.Trademark;
import com.doubleclick.marktinhome.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class TrademarkAdapter extends RecyclerView.Adapter<TrademarkAdapter.TrademarkViewHolder> {

    ArrayList<Trademark> trademarks = new ArrayList<>();
    private Tradmarkinterface tradmarkinterface;

    public TrademarkAdapter(ArrayList<Trademark> trademarks) {
        this.trademarks = trademarks;
    }

    public TrademarkAdapter(ArrayList<Trademark> trademarks, Tradmarkinterface tradmarkinterface) {
        this.trademarks = trademarks;
        this.tradmarkinterface = tradmarkinterface;

    }

    @NonNull
    @Override
    public TrademarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrademarkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trademark, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrademarkViewHolder holder, int position) {
        if (trademarks.size() != 0) {
            Glide.with(holder.itemView.getContext()).load(trademarks.get(holder.getAdapterPosition()).getImage()).into(holder.imageTrademark);
            holder.name.setText(trademarks.get(holder.getAdapterPosition()).getName());
            holder.itemView.setOnClickListener(v -> {
                try {
                    tradmarkinterface.OnItemTradmark(trademarks.get(position));
                } catch (Exception e) {
                    Log.e("ExceptionTradmark", e.getMessage());
                }
            });

            holder.option.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.option_tradmark, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.delete) {
                            tradmarkinterface.onDeleteTradmark(trademarks.get(holder.getAdapterPosition()));
                        }
                        if (id == R.id.edit) {
                            tradmarkinterface.onEditTradmark(trademarks.get(holder.getAdapterPosition()));
                        }
                        return true;
                    }
                });
                popupMenu.show();
            });
        }
    }

    public Trademark getTrademarkAt(int pos) {
        return trademarks.get(pos);
    }


    @Override
    public int getItemCount() {
        return trademarks.size();
    }

    public class TrademarkViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageTrademark;
        private TextView name;
        private ImageView option;

        public TrademarkViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTrademark = itemView.findViewById(R.id.imageTrademark);
            name = itemView.findViewById(R.id.name);
            option = itemView.findViewById(R.id.option);
        }
    }
}
