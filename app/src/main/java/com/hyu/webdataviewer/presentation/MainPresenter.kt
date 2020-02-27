package com.hyu.webdataviewer.presentation

import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
import com.hyu.webdataviewer.util.log.HLog
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainPresenter(private val view: IMainActivityContract.View) :
    IMainActivityContract.Presenter, KoinComponent{

    private val previewFragment by inject<IBaseFragmentContract.View>()

    override fun initLayout() {
        HLog.d("initLayout")
        view.replaceFragment(previewFragment)
    }

}