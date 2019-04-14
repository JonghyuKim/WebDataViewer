package com.hyu.webdataviewer.domain.model

import java.io.Serializable


interface IPreviewModel : Serializable {
    fun getPreviewUrl() : String
    fun getPreviewTitle() : String

}