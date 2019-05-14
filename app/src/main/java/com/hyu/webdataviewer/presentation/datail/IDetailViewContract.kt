package com.hyu.webdataviewer.presentation.datail

import android.os.Bundle
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract

interface IDetailViewContract {
    interface Presenter{
        fun loadData(args : Bundle)
    }

    interface View : IBaseFragmentContract.View{
        fun showDetailView(targetTranslateName : String, model : Any)
    }

    companion object {
        const val ARGS_STR_TRANSITION_NAME = "transitionName"
        const val ARGS_OBJECT_MODEL = "model"
    }
}