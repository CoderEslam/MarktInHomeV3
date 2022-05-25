package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 4/22/2022
 */
public class Group {

    private String cover;
    private String createdBy;
    private String details;
    private String id;
    private String image;
    private String name;
    private long time;


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Group{" +
                "cover='" + cover + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", details='" + details + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                '}';
    }


}
