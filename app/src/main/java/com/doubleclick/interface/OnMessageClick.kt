package com.doubleclick

import android.widget.ProgressBar
import com.doubleclick.marktinhome.Model.Chat

/**
 * Created By Eslam Ghazy on 5/10/2022
 */
interface OnMessageClick {

    fun download(chat: Chat, pos: Int, progressBar: ProgressBar);

    fun deleteForMe(chat: Chat, pos: Int)

    fun deleteForAll(chat: Chat, pos: Int)

}