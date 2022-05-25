package com.doubleclick.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.marktinhome.Model.CommentsGroupData;
import com.doubleclick.marktinhome.Repository.CommentsGroupRepositotry;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/27/2022
 */
public class CommentsGroupViewModel extends ViewModel implements CommentsGroupRepositotry.CommentData {

    MutableLiveData<CommentsGroupData> mutableLiveData = new MutableLiveData<>();
    CommentsGroupRepositotry commentsGroupRepositotry = new CommentsGroupRepositotry(this);

    public CommentsGroupViewModel() {
    }

    public void GetComments(String groupId, String postId) {
        commentsGroupRepositotry.loadComments(groupId, postId);
    }

    public LiveData<CommentsGroupData> getCommentsLiveData() {
        return mutableLiveData;
    }


    @Override
    public void Comments(CommentsGroupData commentsGroupData) {
        mutableLiveData.setValue(commentsGroupData);
    }
}
