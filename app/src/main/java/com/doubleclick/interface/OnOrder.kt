package com.doubleclick

import com.doubleclick.marktinhome.Model.Cart
import com.doubleclick.marktinhome.Model.Orders
import com.doubleclick.marktinhome.Model.OrdersDate

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
interface OnOrder {

    fun OnOKItemOrder(orders: OrdersDate?)
    fun OnCancelItemOrder(orders: OrdersDate?)

}