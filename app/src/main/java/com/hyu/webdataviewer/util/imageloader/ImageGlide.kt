package com.hyu.webdataviewer.util.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.hyu.webdataviewer.R
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory




class ImageGlide : IImageLoader{
    override fun bindImg(context: Context, targetView: ImageView, imgPath: String) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(context)
            .load(imgPath)
            .transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.wait)
            .into(targetView)
    }

    override fun release() {

    }

}
