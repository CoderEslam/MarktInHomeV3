package com.doubleclick.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.Tradmarkinterface;
import com.doubleclick.marktinhome.Adapters.TrademarkAdapter;
import com.doubleclick.marktinhome.Model.Trademark;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/9/2022
 */
public class TrademarkViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView topParents;

    public TrademarkViewHolder(@NonNull View itemView) {
        super(itemView);
        topParents = itemView.findViewById(R.id.topParents);
    }

    public void setTrademark(ArrayList<Trademark> trademarks, Tradmarkinterface tradmarkinterface){
        TrademarkAdapter trademarkAdapter = new TrademarkAdapter(trademarks,tradmarkinterface);
        topParents.setAdapter(trademarkAdapter);
    }
}
