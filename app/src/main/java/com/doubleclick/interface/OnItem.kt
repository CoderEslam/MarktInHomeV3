package com.doubleclick

import com.doubleclick.marktinhome.Model.ParentCategory

/**
 * Created By Eslam Ghazy on 3/5/2022
 */
interface OnItem {
    fun onItem(parentCategory: ParentCategory?)
    fun onItemLong(parentCategory: ParentCategory?)
}