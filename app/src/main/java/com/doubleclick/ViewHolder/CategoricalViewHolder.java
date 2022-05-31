package com.doubleclick.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.Categorical;
import com.doubleclick.OnProduct;
import com.doubleclick.marktinhome.Adapters.CategoricalAdapter;
import com.doubleclick.marktinhome.Adapters.ProductAdapter;
import com.doubleclick.marktinhome.Model.CategoricalProduct;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 5/30/2022
 */
public class CategoricalViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView allCategorical;

    public CategoricalViewHolder(@NonNull View itemView) {
        super(itemView);
        allCategorical = itemView.findViewById(R.id.allCategorical);
    }

    public void setCategorical(ArrayList<CategoricalProduct> categoricalProducts, OnProduct onProduct, Categorical categorical) {
        allCategorical.setAdapter(new CategoricalAdapter(categoricalProducts, onProduct, categorical));
    }

}
