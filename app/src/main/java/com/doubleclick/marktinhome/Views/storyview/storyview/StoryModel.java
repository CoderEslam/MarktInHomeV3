package com.doubleclick.marktinhome.Views.storyview.storyview;

import android.os.Parcel;
import android.os.Parcelable;

public class StoryModel implements Parcelable {

    public String imageUri;
    public String name;
    public String time;
    public String id;


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
    }

    protected StoryModel(Parcel in) {
        imageUri = in.readString();
        name = in.readString();
        time = in.readString();
        id = in.readString();
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
}
