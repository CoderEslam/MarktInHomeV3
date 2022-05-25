package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.CommentsInterface;
import com.doubleclick.marktinhome.Model.Comments;
import com.doubleclick.marktinhome.Repository.CommentsRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class CommentsViewModel extends ViewModel implements CommentsInterface {

    MutableLiveData<ArrayList<Comments>> mutableLiveData = new MutableLiveData<>();

    CommentsRepository commentsRepository = new CommentsRepository(this);

    public CommentsViewModel() {

    }

    public void getCommentsById(String idproduct){
        commentsRepository.getAllComments(idproduct);
    }

    public LiveData<ArrayList<Comments>> getCommentsLiveDate() {
        return mutableLiveData;
    }

    @Override
    public void getComment(@NonNull ArrayList<Comments> comments) {
        mutableLiveData.setValue(comments);
    }
}
