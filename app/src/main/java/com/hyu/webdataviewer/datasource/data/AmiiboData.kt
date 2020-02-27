package com.hyu.webdataviewer.datasource.data

import com.google.gson.annotations.SerializedName

data class AmiiboDatas(@SerializedName("amiibo") val amiiboList : List<AmiiboData>)

data class AmiiboData(val amiiboSeries : String,
                  val character : String,
                  val gameSeries : String,
                  val head : String,
                  val image  : String,
                  val name : String,
                  val tail : String,
                  val type : String)