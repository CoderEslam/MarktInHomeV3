package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.ViewHolder.BannerSliderViewholder;
import com.doubleclick.ViewHolder.BaseViewHolder;
import com.doubleclick.ViewHolder.MyUserViewHolder;
import com.doubleclick.ViewHolder.StoriesViewHolder;
import com.doubleclick.ViewHolder.TrademarkViewHolder;
import com.doubleclick.marktinhome.Model.ChatListModel;
import com.doubleclick.marktinhome.Model.HomeModel;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 7/1/2022
 */
public class ChatListAdapterUserStories extends RecyclerView.Adapter {

    ArrayList<ChatListModel> chatListModels;

    public ChatListAdapterUserStories(ArrayList<ChatListModel> chatListModels) {
        this.chatListModels = chatListModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ChatListModel.STORIES) {
            return new StoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_story_view_recycler, parent, false));
        } else if (viewType == ChatListModel.USERS) {
            return new MyUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_layout_recycler, parent, false));
        } else {
            return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (chatListModels.get(position).getType() == ChatListModel.STORIES) {
            ((StoriesViewHolder) holder).setStories(chatListModels.get(position).getStoryModels());
        } else if (chatListModels.get(position).getType() == ChatListModel.USERS) {
            ((MyUserViewHolder) holder).setAllUser(chatListModels.get(position).onUser, chatListModels.get(position).getChatListData());
        } else {
            ((BaseViewHolder) holder).animationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return chatListModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatListModels.get(position).getType() == ChatListModel.STORIES) {
            return ChatListModel.STORIES;
        } else if (chatListModels.get(position).getType() == ChatListModel.USERS) {
            return ChatListModel.USERS;
        } else {
            return 0;
        }
    }
}
