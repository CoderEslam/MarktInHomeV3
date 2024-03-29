package com.doubleclick.marktinhome.Views.storyview.storyview;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StoryModel implements Parcelable {
    @NonNull
    private String imageUri;
    @NonNull
    private String name;
    @NonNull
    private String time;
    @NonNull
    private String id;
    @NonNull
    private String imageProfile;
    @NonNull
    private String userId;


    public StoryModel(String imageUri, String name, String time, String id) {
        this.imageUri = imageUri;
        this.name = name;
        this.time = time;
        this.id = id;
    }

    public StoryModel(String imageUri, String name, String time) {
        this.imageUri = imageUri;
        this.name = name;
        this.time = time;
    }

    public StoryModel(String imageUri, String time) {
        this.imageUri = imageUri;
        this.time = time;
    }

    public StoryModel() {
        name = "";
        imageUri = "";
        time = "";
        id = "";
        userId = "";
    }

    protected StoryModel(Parcel in) {
        imageUri = in.readString();
        name = in.readString();
        time = in.readString();
        id = in.readString();
    }

    public StoryModel(@NonNull String imageUri, @NonNull String name, @NonNull String time, @NonNull String id, @NonNull String imageProfile, String userId) {
        this.imageUri = imageUri;
        this.name = name;
        this.time = time;
        this.id = id;
        this.imageProfile = imageProfile;
        this.userId = userId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUri);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoryModel> CREATOR = new Creator<StoryModel>() {
        @Override
        public StoryModel createFromParcel(Parcel in) {
            return new StoryModel(in);
        }

        @Override
        public StoryModel[] newArray(int size) {
            return new StoryModel[size];
        }
    };

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StoryModel{" +
                "imageUri='" + imageUri + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @NonNull
    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(@NonNull String imageProfile) {
        this.imageProfile = imageProfile;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
