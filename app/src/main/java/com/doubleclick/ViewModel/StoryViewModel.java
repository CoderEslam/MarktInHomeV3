package com.doubleclick.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Repository.StoryRepository;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 6/28/2022
 */
public class StoryViewModel extends ViewModel implements StoryRepository.GetStories {

    StoryRepository storyRepository = new StoryRepository(this);

    MutableLiveData<ArrayList<ArrayList<StoryModel>>> mutableLiveData = new MutableLiveData<>();

    public StoryViewModel() {
    }

    public LiveData<ArrayList<ArrayList<StoryModel>>> getStoriesLiveData() {
        return mutableLiveData;
    }

    public void Users(List<User> users) {
        storyRepository.getStories(users);
    }

    @Override
    public void getStories(ArrayList<ArrayList<StoryModel>> arrayListArrayList) {
        mutableLiveData.setValue(arrayListArrayList);
    }
}
