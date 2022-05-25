package com.doubleclick

import com.doubleclick.marktinhome.Model.Cart
import com.doubleclick.marktinhome.Model.Orders
import com.doubleclick.marktinhome.Model.Rate

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
interface OnOrder {

    fun OnOKItemOrder(orders: Orders?)
    fun OnCancelItemOrder(orders: Orders?)

}