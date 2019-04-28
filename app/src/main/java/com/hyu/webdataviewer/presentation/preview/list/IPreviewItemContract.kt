package com.hyu.webdataviewer.presentation.preview.list

import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.IBaseItemContract

interface IPreviewItemContract {

    interface Presenter : IBaseItemContract.Presenter<IPreviewModel>{
        fun releaseModels()
    }

    interface View : IBaseItemContract.View<IPreviewModel>
}