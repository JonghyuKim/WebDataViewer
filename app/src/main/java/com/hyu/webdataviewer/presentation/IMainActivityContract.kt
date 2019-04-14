package com.hyu.webdataviewer.presentation

interface IMainActivityContract {
    interface Presenter{
        fun initLayout()
    }

    interface View : IFragmentNavigationContract
}