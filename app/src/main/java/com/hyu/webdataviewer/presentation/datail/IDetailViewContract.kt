package com.hyu.webdataviewer.presentation.datail

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract

interface IDetailViewContract {
    interface Presenter{
        fun loadData(args : Bundle)
        fun bindImage(context: Context, imageView: ImageView, imageUrl : String)
    }

    interface View : IBaseFragmentContract.View{
        fun showDetailView(targetTranslateName : String, model : Any)
        fun startPostponedEnterTransition()
    }

    companion object {
        const val ARGS_STR_TRANSITION_NAME = "transitionName"
        const val ARGS_OBJECT_MODEL = "model"
    }
}