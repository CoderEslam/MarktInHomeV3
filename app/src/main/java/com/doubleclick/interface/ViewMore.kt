package com.doubleclick

import com.doubleclick.marktinhome.Model.Product

/**
 * Created By Eslam Ghazy on 3/18/2022
 */
interface ViewMore {

    fun getViewMore(product: ArrayList<Product>)

    fun getViewMore(product: Product)


}