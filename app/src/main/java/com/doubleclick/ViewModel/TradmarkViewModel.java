package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.Tradmarkinterface;
import com.doubleclick.marktinhome.Model.Trademark;
import com.doubleclick.marktinhome.Repository.TradmarkRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class TradmarkViewModel implements Tradmarkinterface {


    private MutableLiveData<ArrayList<Trademark>> mutableLiveDataAdd = new MutableLiveData<>();
    private MutableLiveData<Trademark> mutableLiveDataEdit = new MutableLiveData<>();
    private MutableLiveData<Trademark> mutableLiveDataDelete = new MutableLiveData<>();

    private MutableLiveData<List<String>> mutableLiveDataNames = new MutableLiveData<>();
    TradmarkRepository tradmarkRepository = new TradmarkRepository(this);

    public TradmarkViewModel() {
        tradmarkRepository.getTradmark();
        tradmarkRepository.getNameTradmark();
    }

    public LiveData<ArrayList<Trademark>> getAllMark() {
        return mutableLiveDataAdd;
    }

    public LiveData<Trademark> MarkEdit() {
        return mutableLiveDataEdit;
    }

    public LiveData<Trademark> MarkDelete() {
        return mutableLiveDataDelete;
    }

    public LiveData<List<String>> getNamesMark() {
        return mutableLiveDataNames;
    }


    @Override
    public void AllNameTradmark(@Nullable List<String> names) {
        mutableLiveDataNames.setValue(names);
    }

    @Override
    public void OnItemTradmark(@Nullable Trademark tradmark) {

    }

    @Override
    public void onEditTradmark(@NonNull Trademark tradmark) {
        mutableLiveDataEdit.setValue(tradmark);
    }

    @Override
    public void onDeleteTradmark(@NonNull Trademark tradmark) {
        mutableLiveDataDelete.setValue(tradmark);
    }

    @Override
    public void AllTradmark(@NonNull ArrayList<Trademark> tradmark) {
        mutableLiveDataAdd.setValue(tradmark);
    }
}
