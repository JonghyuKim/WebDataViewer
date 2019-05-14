package com.hyu.webdataviewer.domain.model

data class AmiiboModel(val amiiboSeries : String,
                       val character : String,
                       val gameSeries : String,
                       override val image: String,
                       override val name: String
): IPreviewModel