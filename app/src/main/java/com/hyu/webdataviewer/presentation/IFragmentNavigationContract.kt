package com.hyu.webdataviewer.presentation

import android.view.View
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract

interface IFragmentNavigationContract {

    /**
     *  Fragment replace
     */
    fun replaceFragment(fragment: IBaseFragmentContract.View)

    /**
     * Fragment add
     */
    fun addFragment(fragment: IBaseFragmentContract.View)

    /**
     * Fragment useBackstack & transition
     */
    fun addFragment(fragment: IBaseFragmentContract.View, transitionView: View)
}