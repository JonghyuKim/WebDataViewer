package com.hyu.webdataviewer.domain.model

data class AmiiboModel(val amiiboSeries : String,
                       val character : String,
                       val gameSeries : String,
                       val image  : String,
                       val name : String): IPreviewModel {

    override fun getPreviewUrl(): String {
        return image
    }

    override fun getPreviewTitle(): String {
        return name
    }
}