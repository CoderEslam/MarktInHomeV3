package com.doubleclick

import com.doubleclick.marktinhome.Model.User

/**
 * Created By Eslam Ghazy on 3/3/2022
 */
interface UserInter {
    fun ItemUser(user: User?)
    fun ItemUserInfoById(user: User?)
    fun AllUser(user: ArrayList<User>?)
    fun OnUserLisitner(user: User?)

}