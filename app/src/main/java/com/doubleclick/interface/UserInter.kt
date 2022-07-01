package com.doubleclick

import android.widget.ImageView
import com.doubleclick.marktinhome.Model.ChatList
import com.doubleclick.marktinhome.Model.User

/**
 * Created By Eslam Ghazy on 3/3/2022
 */
interface UserInter {
    fun ItemUser(user: User)
    fun ItemUserChanged(user: User)
    fun ItemUserDeleted(user: User, chatList: ChatList)
    fun ItemUserInfoById(user: User?)
    fun AllUser(user: ArrayList<User>?)
    fun OnUserLisitner(user: User?)

    fun OnImageListnerLoad(user: User, image: ImageView)


}