package com.doubleclick.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.Adapters.StoriesAdapter;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 7/1/2022
 */
public class StoriesViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView stories;

    public StoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        stories = itemView.findViewById(R.id.stories);
    }

    public void setStories(ArrayList<ArrayList<StoryModel>> storyModels) {
        stories.setAdapter(new StoriesAdapter(storyModels));
    }
}
