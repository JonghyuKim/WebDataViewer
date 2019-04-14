package com.hyu.webdataviewer.presentation.base

import android.os.Bundle
import com.hyu.webdataviewer.presentation.IFragmentNavigationContract

interface IBaseFragmentContract{
    interface Presenter

    interface View : IFragmentNavigationContract {
        /**
         * Fragment Argument Setting
         */
        fun setArgument(args : Bundle)

        /**
         * Fragment Page Switching manager setting
         */
        fun setNavigation(fragmentNavigation : IFragmentNavigationContract)

        /**
         * View convert to fragment
         */
        fun toFragment() : androidx.fragment.app.Fragment
    }
}