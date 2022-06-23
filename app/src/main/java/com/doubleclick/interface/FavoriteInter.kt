package com.doubleclick

import com.doubleclick.marktinhome.Model.Favorite
import com.doubleclick.marktinhome.Model.Product
import java.util.ArrayList

/**
 * Created By Eslam Ghazy on 6/23/2022
 */
interface FavoriteInter {
    fun isFavorite(b: Boolean)
    fun myFavorite(favorites: ArrayList<Product>);
    fun count(c: Long)
}