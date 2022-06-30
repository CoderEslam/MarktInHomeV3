package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.Model.Constantes.STORIES;
import static com.doubleclick.marktinhome.Views.storyview.utils.Utils.getDurationBetweenDates;

import android.util.Log;

import androidx.annotation.NonNull;

import com.airbnb.lottie.L;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
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
        for (User user : users) {
            reference.child(STORIES).child(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ArrayList<StoryModel> storyModels = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            StoryModel storyModel = dataSnapshot.getValue(StoryModel.class);
                            assert storyModel != null;
                            storyModels.add(new StoryModel(storyModel.getImageUri(), user.getName(), storyModel.getTime(), storyModel.getId(), user.getImage()));

//                            Log.e("DDDDDDDDDDDDDDDDDDDDDDD", "" + getDurationBetweenDates(Long.parseLong(storyModel.getTime()), Calendar.getInstance().getTime().getTime()));
//                            if (getDurationBetweenDates(Long.parseLong(storyModel.getTime()), Calendar.getInstance().getTime().getTime()) < 24) {
//
//                            }

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

    private long getDurationBetweenDates(long d1, long d2) {

        long diff = d1 - d2;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

//        if (days != 0) {
//            return Math.abs(days);
//        }
        if (hours != 0) {
            return Math.abs(hours);
        } else {
            return 0;
        }
//        if (minutes != 0) {
//            return Math.abs(minutes);
//        }
//        if (seconds != 0) {
//            return Math.abs(seconds);
//        }

    }
}
