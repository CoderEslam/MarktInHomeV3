package com.doubleclick.marktinhome.Model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class Trademark implements Serializable {

    private String id;
    private String name;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Trademark{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trademark)) return false;
        Trademark trademark = (Trademark) o;
        return getId().equals(trademark.getId()) && Objects.equals(getName(), trademark.getName()) && getImage().equals(trademark.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImage());
    }
}
