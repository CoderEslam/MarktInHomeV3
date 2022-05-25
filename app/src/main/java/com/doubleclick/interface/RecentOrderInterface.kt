package com.doubleclick

import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.Model.RecentOrder

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
interface RecentOrderInterface {
    fun getMyRecentOrder(recentOrder: ArrayList<RecentOrder?>?)

}