package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.CommentsInterface;
import com.doubleclick.marktinhome.Model.Comments;
import com.doubleclick.marktinhome.Model.CommentsProductData;
import com.doubleclick.marktinhome.Repository.CommentsRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class CommentsViewModel extends ViewModel implements CommentsInterface {

    MutableLiveData<CommentsProductData> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<CommentsProductData> mutableLiveDataChanged = new MutableLiveData<>();
    MutableLiveData<CommentsProductData> mutableLiveDataDeleted = new MutableLiveData<>();

    CommentsRepository commentsRepository = new CommentsRepository(this);

    public CommentsViewModel() {

    }

    public void getCommentsById(String idproduct) {
        commentsRepository.getAllComments(idproduct);
    }

    public LiveData<CommentsProductData> getCommentsLiveDate() {
        return mutableLiveData;
    }

    public LiveData<CommentsProductData> getCommentsLiveDateChanged() {
        return mutableLiveDataChanged;
    }

    public LiveData<CommentsProductData> getCommentsLiveDateDeleted() {
        return mutableLiveDataDeleted;
    }

    @Override
    public void getComment(@NonNull CommentsProductData comment) {
        mutableLiveData.setValue(comment);
    }

    @Override
    public void getCommentChanged(@NonNull CommentsProductData comment) {
        mutableLiveDataChanged.setValue(comment);
    }

    @Override
    public void getCommentDeleted(@NonNull CommentsProductData comment) {
        mutableLiveDataDeleted.setValue(comment);
    }
}
