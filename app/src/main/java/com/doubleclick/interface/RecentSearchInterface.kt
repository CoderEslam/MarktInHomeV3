package com.doubleclick

import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.Model.RecentSearch

/**
 * Created By Eslam Ghazy on 3/11/2022
 */
interface RecentSearchInterface {
    fun getLastListSearchAboutProductOneTime(recentSearchaboutProduct: ArrayList<Product?>?)
    fun getRecentSearch(recentSearch:RecentSearch)

}