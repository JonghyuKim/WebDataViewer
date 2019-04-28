package com.hyu.webdataviewer.presentation.preview.list

import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.IBaseItemContract

class PreviewItemPresenter(private val view: IPreviewItemContract.View) : IPreviewItemContract.Presenter{
    override fun setItemModel(
        model: IPreviewModel,
        itemClickListener: IBaseItemContract.ItemClickListener<IPreviewModel>?
    ) {
        view.showItemView(model,itemClickListener)
    }

    override fun releaseModels() {
    }



}