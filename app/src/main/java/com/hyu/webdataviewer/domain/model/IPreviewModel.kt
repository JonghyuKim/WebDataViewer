package com.hyu.webdataviewer.domain.model

import java.io.Serializable


interface IPreviewModel : Serializable{
    val image  : String
    val name : String
}