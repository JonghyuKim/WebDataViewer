package com.hyu.webdataviewer.presentation.base

import android.os.Bundle
import android.view.View
import com.hyu.webdataviewer.presentation.IFragmentNavigationContract

abstract class BaseFragment : androidx.fragment.app.Fragment(), IBaseFragmentContract.View{

    private val fragmentNavigation : IFragmentNavigationContract get() { return activity as IFragmentNavigationContract}
    lateinit var args : Bundle

    override fun setArgument(args: Bundle) {
        this.args = args
    }

    override fun toFragment(): androidx.fragment.app.Fragment {
        return this
    }

    override fun replaceFragment(fragment: IBaseFragmentContract.View){
        fragmentNavigation.replaceFragment(fragment)
    }

    override fun replaceFragment(fragment: IBaseFragmentContract.View, transitionView: View, transitionName : String?) {
        fragmentNavigation.replaceFragment(fragment, transitionView, transitionName)
    }
}