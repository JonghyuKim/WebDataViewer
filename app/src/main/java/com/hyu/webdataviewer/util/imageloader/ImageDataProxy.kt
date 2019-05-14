package com.hyu.webdataviewer.util.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.hyu.webdataviewer.R
import com.hyu.webdataviewer.util.log.HLog

/**
 * DummyImageDataLoader
 */
class ImageDataProxy : IImageLoader {
    override var onCompliteBinding: (() -> Unit)? = null

    private var loadImg: Bitmap? = null

    var compliteListener: ((Bitmap) -> Unit)? = null

    override fun bindImg(context: Context, targetView: ImageView, imgPath : String) {

        loadImg?.let{
            targetView.setImageBitmap(it)
        } ?: let{
            targetView.setImageResource(R.drawable.wait)
        }
    }

    override fun release() {

        //proxy Load cancel!
        loadImg?.recycle()
        loadImg = null
    }

    private fun getDefaultImg(context : Context): Bitmap{
        HLog.d("context : $context" )
        return BitmapFactory.decodeResource(context.resources, R.drawable.wait)
    }
}