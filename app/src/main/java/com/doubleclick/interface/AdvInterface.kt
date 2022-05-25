package com.doubleclick

import com.doubleclick.marktinhome.Model.Advertisement

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
interface AdvInterface {

    fun AllAdvertisement(advertisement: ArrayList<Advertisement>)
    fun OnEditAdvertisement(advertisement: Advertisement)
    fun OnDeleteAdvertisement(advertisement: Advertisement)

}