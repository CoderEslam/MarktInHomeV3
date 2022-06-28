package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryViewCircle;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 6/28/2022
 */
public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder> {


    ArrayList<ArrayList<StoryModel>> storyModels;

    public StoriesAdapter(ArrayList<ArrayList<StoryModel>> storyModels) {
        this.storyModels = storyModels;
    }


    @NonNull
    @Override
    public StoriesAdapter.StoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_story_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesAdapter.StoriesViewHolder holder, int position) {
        holder.storyViewCircle.setActivityContext(holder.itemView.getContext());
        holder.storyViewCircle.resetStoryVisits();
        holder.storyViewCircle.setImageUris(storyModels.get(position));
    }

    @Override
    public int getItemCount() {
        return storyModels.size();
    }

    public class StoriesViewHolder extends RecyclerView.ViewHolder {
        private StoryViewCircle storyViewCircle;

        public StoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            storyViewCircle = itemView.findViewById(R.id.storyView);

        }
    }
}
