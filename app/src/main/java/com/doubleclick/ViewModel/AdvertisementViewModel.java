package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.AdvInterface;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.doubleclick.marktinhome.Repository.AdvertisementRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class AdvertisementViewModel implements AdvInterface {

    MutableLiveData<ArrayList<Advertisement>> mutableLiveDataAdd = new MutableLiveData<>();
    MutableLiveData<Advertisement> mutableLiveDataDelete = new MutableLiveData<>();
    MutableLiveData<Advertisement> mutableLiveDataEdit = new MutableLiveData<>();

    AdvertisementRepository repository = new AdvertisementRepository(this);

    public AdvertisementViewModel() {
        repository.getAdvertisement();
    }

    public LiveData<ArrayList<Advertisement>> getAdvAdd() {
        return mutableLiveDataAdd;
    }

    public LiveData<Advertisement> getAdvEdit() {
        return mutableLiveDataEdit;
    }

    public LiveData<Advertisement> getAdvDelete() {
        return mutableLiveDataDelete;
    }


    @Override
    public void OnEditAdvertisement(@NonNull Advertisement advertisement) {
        mutableLiveDataEdit.setValue(advertisement);
    }

    @Override
    public void OnDeleteAdvertisement(@NonNull Advertisement advertisement) {
        mutableLiveDataDelete.setValue(advertisement);
    }


    @Override
    public void AllAdvertisement(@NonNull ArrayList<Advertisement> advertisement) {
        mutableLiveDataAdd.setValue(advertisement);
    }
}
