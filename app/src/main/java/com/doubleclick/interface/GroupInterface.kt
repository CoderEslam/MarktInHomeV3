package com.doubleclick

import com.doubleclick.marktinhome.Model.Group
import com.doubleclick.marktinhome.Model.GroupData

/**
 * Created By Eslam Ghazy on 4/22/2022
 */
interface GroupInterface {

//    fun myGroups(groups: ArrayList<Group>)
    fun allGroups(groups: ArrayList<Group>)
    fun groupData(groups: GroupData)

    fun myGroups(groups: Group)
    fun allGroups(groups: Group)


}