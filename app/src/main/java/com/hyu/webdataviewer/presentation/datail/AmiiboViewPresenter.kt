package com.hyu.webdataviewer.presentation.datail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import com.hyu.webdataviewer.domain.model.AmiiboModel
import com.hyu.webdataviewer.util.imageloader.IImageLoader
import com.hyu.webdataviewer.util.log.HLog
import org.koin.core.KoinComponent
import org.koin.core.inject

class AmiiboViewPresenter(private val view: IDetailViewContract.View) : IDetailViewContract.Presenter, KoinComponent{

    private val imageLoader by inject<IImageLoader>()

    override fun loadData(args: Bundle) {

        var transitionName  = ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionName = args.getString("transitionName")
        }

        HLog.d("loadModel : $transitionName")

        var amiiboModel = args.getSerializable("model") as AmiiboModel

        view.showDetailView(transitionName, amiiboModel)
    }

    override fun bindImage(context: Context, imageView: ImageView, imageUrl: String) {
        imageLoader.bindImg(context, imageView, imageUrl)
    }

}