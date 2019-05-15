package com.hyu.webdataviewer.presentation

import android.view.View
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
import com.hyu.webdataviewer.util.log.HLog
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainPresenter(private val view: IMainActivityContract.View) :
    IMainActivityContract.Presenter, KoinComponent{

    private val previewFragment by inject<IBaseFragmentContract.View>()

    private val fragmentNavigation = object : IFragmentNavigationContract{

        override fun replaceFragment(fragment: IBaseFragmentContract.View) {
            fragment.setNavigation(this)
            view.replaceFragment(fragment)
        }

        override fun replaceFragment(fragment: IBaseFragmentContract.View, transitionView: View) {
            fragment.setNavigation(this)
            view.replaceFragment(fragment, transitionView)
        }
    }

    override fun initLayout() {
        HLog.d("initLayout")
        setNavigator(previewFragment)
        view.replaceFragment(previewFragment)
    }

    override fun setNavigator(fragment: IBaseFragmentContract.View) {
        fragment.setNavigation(fragmentNavigation)
    }

}