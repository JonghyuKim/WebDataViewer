package com.hyu.webdataviewer.presentation.datail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyu.webdataviewer.presentation.base.BaseFragment

abstract class DetailFragment : BaseFragment(), IDetailViewContract.View{

    lateinit var mainLayout : View

    abstract val presenter : IDetailViewContract.Presenter
    abstract val layoutId : Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mainLayout = inflater.inflate(layoutId, container, false)
        presenter.loadData(args)

        return mainLayout
    }
}