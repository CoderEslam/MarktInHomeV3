package com.doubleclick.marktinhome.Model;

import java.io.Serializable;
import java.util.Objects;

public class Advertisement implements Serializable {

    private String image;
    private String id;
    private String text;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
