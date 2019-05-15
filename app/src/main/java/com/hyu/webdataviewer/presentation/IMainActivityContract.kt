package com.hyu.webdataviewer.presentation

import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract

interface IMainActivityContract {
    interface Presenter{
        fun initLayout()
        fun setNavigator(fragment: IBaseFragmentContract.View)
    }

    interface View : IFragmentNavigationContract
}