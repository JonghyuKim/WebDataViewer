package com.hyu.webdataviewer.presentation.preview.list

import android.content.Context
import android.widget.ImageView
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.presentation.base.IBaseItemContract
import com.hyu.webdataviewer.util.imageloader.IImageLoader
import org.koin.core.KoinComponent
import org.koin.core.inject

class PreviewItemPresenter(private val view: IPreviewItemContract.View) : IPreviewItemContract.Presenter, KoinComponent{
    override fun setItemModel(
        model: IPreviewModel,
        itemClickListener: IBaseItemContract.ItemClickListener<IPreviewModel>?
    ) {
        view.showItemView(model,itemClickListener)
    }

    private val imageLoader by inject<IImageLoader>()

    override fun bindImage(context: Context, imageView: ImageView, imageUrl: String) {
        imageLoader.bindImg (context, imageView, imageUrl)

    }
    override fun releaseModels() {
        imageLoader.release()
    }



}