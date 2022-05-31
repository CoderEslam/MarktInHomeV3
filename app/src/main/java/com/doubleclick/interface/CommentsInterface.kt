package com.doubleclick

import com.doubleclick.marktinhome.Model.Comments
import com.doubleclick.marktinhome.Model.CommentsProductData

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
interface CommentsInterface {

    fun getComment(comment: CommentsProductData)

    fun getCommentChanged(comment: CommentsProductData)

    fun getCommentDeleted(comment: CommentsProductData)


}