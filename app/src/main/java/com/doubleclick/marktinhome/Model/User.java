package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class User implements Serializable {


    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String email;
    @NonNull
    private String id;
    @NonNull
    private String password;
    @NonNull
    private String phone;
    @NonNull
    private String image;
    @NonNull
    private String token;

    public User() {
        name = "";
        address = "";
        email = "";
        id = "";
        password = "";
        phone = "";
        image = "";
        token = "";
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getName().equals(user.getName()) && getAddress().equals(user.getAddress()) && getEmail().equals(user.getEmail()) && getId().equals(user.getId()) && getPassword().equals(user.getPassword()) && getPhone().equals(user.getPhone()) && getImage().equals(user.getImage()) && getToken().equals(user.getToken()) && Objects.equals(getStatus(), user.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getEmail(), getId(), getPassword(), getPhone(), getImage(), getToken(), getStatus());
    }
}
