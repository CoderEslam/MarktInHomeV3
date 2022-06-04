package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Advertisement implements Serializable {

    @NonNull
    private String image;
    @NonNull
    private String id;
    @NonNull
    private String text;

    public Advertisement() {
        image = "";
        id = "";
        text = "";
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(getImage(), that.getImage()) && getId().equals(that.getId()) && Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImage(), getId(), getText());
    }
}
