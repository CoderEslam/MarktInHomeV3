package com.doubleclick

import com.doubleclick.marktinhome.Model.Orders
import com.doubleclick.marktinhome.Model.OrdersDate

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
interface OrderLisinter {

    fun OnOrder(orders: ArrayList<OrdersDate>)
    fun CountOrder(c: Long)

}