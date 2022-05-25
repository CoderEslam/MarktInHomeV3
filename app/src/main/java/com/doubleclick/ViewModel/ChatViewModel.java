package com.doubleclick.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Repository.ChatReopsitory;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class ChatViewModel extends ViewModel implements ChatReopsitory.Chats {

    MutableLiveData<Chat> newInsertChat = new MutableLiveData<>();

    ChatReopsitory chatReopsitory = new ChatReopsitory(this);

    public ChatViewModel() {
    }

    public void ChatById(String userId, ChatReopsitory.StatusChat statusChat) {
        chatReopsitory.getChats(userId, statusChat);
    }


    public LiveData<Chat> newInsertChat() {
        return newInsertChat;
    }


    @Override
    public void newInsertChat(Chat chat) {
        newInsertChat.setValue(chat);
    }
}
