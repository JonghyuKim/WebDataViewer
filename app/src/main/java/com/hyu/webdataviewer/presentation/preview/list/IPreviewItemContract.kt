package com.hyu.webdataviewer.presentation.preview.list

import android.content.Context
import android.widget.ImageView
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.IBaseItemContract

interface IPreviewItemContract {

    interface Presenter : IBaseItemContract.Presenter<IPreviewModel>{
        fun bindImage(context: Context, imageView: ImageView, imageUrl : String)
        fun releaseModels()
    }

    interface View : IBaseItemContract.View<IPreviewModel>
}