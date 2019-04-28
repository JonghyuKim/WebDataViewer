package com.hyu.webdataviewer.util.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hyu.webdataviewer.R
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory




class ImageGlide : IImageLoader{
    override var onCompliteBinding: (() -> Unit)? = null

    override fun bindImg(context: Context, targetView: ImageView, imgPath: String) {

        Glide.with(context)
            .load(imgPath)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.wait)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onCompliteBinding?.invoke()
                    return false
                }
            })
            .into(targetView)
    }

    override fun release() {

    }

}
