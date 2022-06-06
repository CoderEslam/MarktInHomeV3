package com.doubleclick

import com.doubleclick.marktinhome.Model.ChatList

/**
 * Created By Eslam Ghazy on 6/6/2022
 */
interface ChatListInter {

    fun insert(chatList: ChatList);
    fun update(chatList: ChatList);
    fun delete(chatList: ChatList);

}