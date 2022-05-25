package com.doubleclick

import com.doubleclick.marktinhome.Model.Cart

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
interface OnCartLisnter {

    fun getCart(cart: Cart)
    fun OnAddItemOrder(cart: Cart, pos: Int)
    fun OnMinsItemOrder(cart: Cart, pos: Int)
    fun OnDeleteItemOrder(cart: Cart, pos: Int)

}