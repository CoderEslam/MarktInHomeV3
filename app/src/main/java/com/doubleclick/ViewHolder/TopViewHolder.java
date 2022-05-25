package com.doubleclick.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.OnItem;
import com.doubleclick.marktinhome.Adapters.AllCategoryAdapter;
import com.doubleclick.marktinhome.Model.ParentCategory;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class TopViewHolder extends RecyclerView.ViewHolder {


    private RecyclerView topParents;

    public TopViewHolder(@NonNull View itemView) {
        super(itemView);
        topParents = itemView.findViewById(R.id.topParents);
    }

    public void setParent(ArrayList<ParentCategory> parentCategories, OnItem onItem) {
        AllCategoryAdapter allCategoryAdapter = new AllCategoryAdapter(parentCategories, onItem);
        topParents.setAdapter(allCategoryAdapter);
    }

}