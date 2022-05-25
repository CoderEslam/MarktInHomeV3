package com.doubleclick.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.marktinhome.Model.PostData;
import com.doubleclick.marktinhome.Model.PostsGroup;
import com.doubleclick.marktinhome.Repository.PostsRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/26/2022
 */
public class PostsViewModel extends ViewModel implements PostsRepository.PostInterface {


    private MutableLiveData<ArrayList<PostData>> mutableLiveData = new MutableLiveData<>();

    PostsRepository postsRepository = new PostsRepository(this);

    public PostsViewModel() {

    }

    public void loadPosts(String groupId,int num) {
        postsRepository.AllPosts(groupId,num);
    }

    public LiveData<ArrayList<PostData>> getPosts() {
        return mutableLiveData;
    }

    @Override
    public void allPosts(ArrayList<PostData> postsGroups) {
        mutableLiveData.setValue(postsGroups);
    }
}
