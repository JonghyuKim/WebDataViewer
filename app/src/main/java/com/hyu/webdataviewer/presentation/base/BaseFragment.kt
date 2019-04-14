package com.hyu.webdataviewer.presentation.base

import android.os.Bundle
import android.view.View
import com.hyu.webdataviewer.presentation.IFragmentNavigationContract

abstract class BaseFragment : androidx.fragment.app.Fragment(), IBaseFragmentContract.View{

    private lateinit var fragmentNavigation : IFragmentNavigationContract
    lateinit var args : Bundle

    override fun setArgument(args: Bundle) {
        this.args = args
    }

    override fun setNavigation(fragmentNavigation: IFragmentNavigationContract) {
        this.fragmentNavigation = fragmentNavigation
    }

    override fun toFragment(): androidx.fragment.app.Fragment {
        return this
    }

    override fun replaceFragment(fragment: IBaseFragmentContract.View){
        fragmentNavigation.replaceFragment(fragment)
    }
    override fun addFragment(fragment: IBaseFragmentContract.View){
        fragmentNavigation.addFragment(fragment)
    }

    override fun addFragment(fragment: IBaseFragmentContract.View, transitionView: View) {
        fragmentNavigation.addFragment(fragment, transitionView)
    }
}