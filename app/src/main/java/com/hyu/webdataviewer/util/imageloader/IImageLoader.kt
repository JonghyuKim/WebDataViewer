package com.hyu.webdataviewer.util.imageloader

import android.content.Context
import android.widget.ImageView

interface IImageLoader {
    fun bindImg(context: Context, targetView: ImageView, imgPath : String)
    var onCompliteBinding : (() -> Unit)?

    fun release()
}