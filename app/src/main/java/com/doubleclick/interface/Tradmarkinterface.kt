package com.doubleclick

import com.doubleclick.marktinhome.Model.Trademark

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
interface Tradmarkinterface {

    fun AllTradmark(tradmark: ArrayList<Trademark>)
    fun AllNameTradmark(names: List<String>)
    fun OnItemTradmark(tradmark: Trademark)
    fun onEditTradmark(tradmark: Trademark)
    fun onDeleteTradmark(tradmark: Trademark)

}