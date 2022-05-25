package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 4/23/2022
 */
public class GroupData {

    private Group group;

    public GroupData() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;


//    public GroupData(Group group) {
//        this.group = group;
//    }

    public GroupData(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    @Override
    public String toString() {
        return "GroupData{" +
                "group=" + group +
                ", user=" + user +
                '}';
    }
}
