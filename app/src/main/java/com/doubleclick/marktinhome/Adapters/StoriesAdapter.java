package com.doubleclick.marktinhome.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.ViewHolder.StoriesViewHolder;
import com.doubleclick.marktinhome.MainActivity;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.CircleImageView;
import com.doubleclick.marktinhome.Views.circularstatusview.CircularStatusView;
import com.doubleclick.marktinhome.Views.storyview.StoryView;
import com.doubleclick.marktinhome.Views.storyview.callback.OnStoryChangedCallback;
import com.doubleclick.marktinhome.Views.storyview.callback.StoryClickListeners;
import com.doubleclick.marktinhome.Views.storyview.progress.StoriesProgressView;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryViewCircle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
        try {
            Log.e("TEXXXXXXXXXXXT", "" + storyModels.get(position));
            if (storyModels.get(position).size() != 0) {
                Glide.with(holder.itemView.getContext()).load(storyModels.get(position).get(0).getImageUri()).into(holder.image);
                holder.circular_status_view.setPortionsCount(storyModels.get(position).size());
                holder.circular_status_view.setPortionSpacing(10);
                holder.circular_status_view.setPortionsColor(holder.itemView.getContext().getResources().getColor(R.color.green));
                holder.circular_status_view.setPortionWidth(4f);
                holder.itemView.setOnClickListener(view -> {
                    new StoryView.Builder(((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager())
                            .setStoriesList(storyModels.get(position))
                            .setStoryDuration(5000)
                            .setTitleText(holder.itemView.getContext().getResources().getString(R.string.name))
                            .setSubtitleText(holder.itemView.getContext().getResources().getString(R.string.egypt))
                            .setStoryClickListeners(new StoryClickListeners() {
                                @Override
                                public void onDescriptionClickListener(int position) {

                                }

                                @Override
                                public void onTitleIconClickListener(int position) {
                                }
                            }).setOnStoryChangedCallback(new OnStoryChangedCallback() {
                                @Override
                                public void storyChanged(int position) {

                                }
                            }).setStartingIndex(0)
                            .setRtl(true)
                            .build()
                            .show();
                });
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e("Expetion", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return storyModels.size();
    }

    public class StoriesViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private CircularStatusView circular_status_view;

        public StoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            circular_status_view = itemView.findViewById(R.id.circular_status_view);
        }
    }
}
