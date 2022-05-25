package com.doubleclick

import com.doubleclick.marktinhome.Model.ChildCategory
import com.doubleclick.marktinhome.Model.ClassificationPC
import com.doubleclick.marktinhome.Model.ParentCategory
import com.doubleclick.marktinhome.Model.Product
import java.util.*

/**
 * Created By Eslam Ghazy on 3/3/2022
 */
interface Products {

    fun product(products: ArrayList<ArrayList<ArrayList<Product>>>)
    fun Parentproduct(Parentproduct: ArrayList<ParentCategory?>?)
    fun Childproduct(Childproduct: ArrayList<ChildCategory?>?)
    fun Childrenproduct(Childproduct: ArrayList<ChildCategory?>?)
    fun getProduct(Product: ArrayList<Product?>?)
    fun getQueryProducts(QueryProducts: ArrayList<Product?>?)
    fun getQueryByParents(QueryProducts: ArrayList<Product?>?)
    fun getQueryByChild(QueryProducts: ArrayList<Product?>?)
    fun getProductWithTrademark(productWithTrademark: ArrayList<Product?>?)
    fun getProductTopDeals(topDeals: ArrayList<Product?>?)
    fun getProductById(productById: ArrayList<Product?>?)

    //    fun getLastSearchProduct(LastSearchproduct: ArrayList<Product?>?)
    fun getClassificationPC(Product: ArrayList<ClassificationPC?>?)


}