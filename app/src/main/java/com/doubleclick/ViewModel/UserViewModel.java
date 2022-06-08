package com.doubleclick.ViewModel;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Repository.UserRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class UserViewModel extends ViewModel implements UserInter {

    MutableLiveData<User> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> mutableLiveDataUserInfo = new MutableLiveData<>();

    MutableLiveData<ArrayList<User>> arrayListMutableLiveData = new MutableLiveData<>();
    UserRepository userRepository = new UserRepository(this);

    public UserViewModel() {
        userRepository.getUser();
        userRepository.getAllUser("");
    }

    public void getUserByName(String name) {
        userRepository.getAllUser(name);
    }

    public void getUserById(String id) {
        userRepository.getInfoUserById(id);
    }


    public LiveData<User> getUser() {
        return mutableLiveData;
    }

    public LiveData<User> getUserInfo() {
        return mutableLiveDataUserInfo;
    }


    public LiveData<ArrayList<User>> getAllUsers() {
        return arrayListMutableLiveData;
    }

    @Override
    public void ItemUser(User user) {
        mutableLiveData.setValue(user);
    }

    @Override
    public void AllUser(@Nullable ArrayList<User> users) {
        arrayListMutableLiveData.setValue(users);
    }

    @Override
    public void OnUserLisitner(@NonNull User user) {
        // nothing
    }

    @Override
    public void ItemUserInfoById(@Nullable User user) {
        mutableLiveDataUserInfo.setValue(user);
    }

    @Override
    public void ItemUserChanged(@NonNull User user) {
        // non
    }

    @Override
    public void ItemUserDeleted(@NonNull User user) {
        //non
    }

    @Override
    public void OnImageListnerLoad(@NonNull User user, @NonNull ImageView image) {
        //non
    }
}
