package com.doubleclick

import com.doubleclick.marktinhome.Model.Cart
import com.doubleclick.marktinhome.Model.CartData

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
interface OnCartLisnter {

    fun getCart(cart: CartData)
    fun OnAddItemOrder(cart: CartData, pos: Int)
    fun OnMinsItemOrder(cart: CartData, pos: Int)
    fun OnDeleteItemOrder(cart: CartData, pos: Int)

}