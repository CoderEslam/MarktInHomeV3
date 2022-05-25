package com.doubleclick.marktinhome.Model;

import java.io.Serializable;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class User implements Serializable {


    public User() {
    }

    private String name;
    private String address;
    private String email;
    private String id;
    private String password;
    private String phone;
    private String image;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
