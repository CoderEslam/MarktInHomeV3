package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.MainActivity;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.storyview.StoryView;
import com.doubleclick.marktinhome.Views.storyview.callback.OnStoryChangedCallback;
import com.doubleclick.marktinhome.Views.storyview.callback.StoryClickListeners;
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
       /* holder.itemView.setOnClickListener(view -> {
            new StoryView.Builder(((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager())
                    .setStoriesList(storyModels.get(position))
                    .setStoryDuration(5000)
                    .setTitleText("Hamza Al-Omari")
                    .setSubtitleText("Damascus")
                    .setStoryClickListeners(new StoryClickListeners() {
                        @Override
                        public void onDescriptionClickListener(int position) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked: " + storyModels.get(position), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onTitleIconClickListener(int position) {
                        }
                    })
                    .setOnStoryChangedCallback(new OnStoryChangedCallback() {
                        @Override
                        public void storyChanged(int position) {
                            Toast.makeText(holder.itemView.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setStartingIndex(0)
                    .setRtl(true)
                    .build()
                    .show();
        });*/
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
