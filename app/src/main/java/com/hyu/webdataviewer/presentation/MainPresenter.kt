package com.hyu.webdataviewer.presentation

import android.view.View
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
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

        override fun addFragment(fragment: IBaseFragmentContract.View) {
            fragment.setNavigation(this)
            view.addFragment(fragment)
        }

        override fun addFragment(fragment: IBaseFragmentContract.View, transitionView: View) {
            fragment.setNavigation(this)
            view.addFragment(fragment, transitionView)
        }
    }

    override fun initLayout() {
        previewFragment.setNavigation(fragmentNavigation)
        view.addFragment(previewFragment)
    }

}