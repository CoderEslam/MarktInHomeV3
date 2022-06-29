package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.Model.Constantes.STORIES;

import android.util.Log;

import androidx.annotation.NonNull;

import com.airbnb.lottie.L;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 6/28/2022
 */
public class StoryRepository extends BaseRepository {

    ArrayList<ArrayList<StoryModel>> arrayListArrayList = new ArrayList<>();
    GetStories getStories;

    public StoryRepository(GetStories getStories) {
        this.getStories = getStories;
    }

    public void getStories(List<User> users) {
        Log.e("USERSSS", users.toString());
        for (User user : users) {
            reference.child(STORIES).child(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ArrayList<StoryModel> storyModels = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            StoryModel storyModel = dataSnapshot.getValue(StoryModel.class);
                            storyModels.add(storyModel);
                        }
                        arrayListArrayList.add(storyModels);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        getStories.getStories(arrayListArrayList);
    }

    public interface GetStories {
        void getStories(ArrayList<ArrayList<StoryModel>> arrayListArrayList);
    }

}
