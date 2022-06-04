package com.doubleclick.marktinhome.Views.imageslider.interfaces

import com.doubleclick.marktinhome.Views.imageslider.constants.ActionTypes


interface TouchListener {
    /**
     * Click listener touched item function.
     *
     * @param  touched  slider boolean
     */
    fun onTouched(touched: ActionTypes)
}