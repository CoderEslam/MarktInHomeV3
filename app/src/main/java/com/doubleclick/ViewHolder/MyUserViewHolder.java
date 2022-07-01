package com.doubleclick.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Adapters.MyUserChatListAdapter;
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListData;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 7/1/2022
 */
public class MyUserViewHolder extends RecyclerView.ViewHolder {

    private ShimmerRecyclerView allUser;

    public MyUserViewHolder(@NonNull View itemView) {
        super(itemView);
        allUser = itemView.findViewById(R.id.allUser);
    }

    public void setAllUser(UserInter onUser, List<ChatListData> chatListData) {
        allUser.setAdapter(new MyUserChatListAdapter(onUser, chatListData));
    }

}
