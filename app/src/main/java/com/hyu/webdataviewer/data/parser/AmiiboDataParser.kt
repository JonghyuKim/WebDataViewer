package com.hyu.webdataviewer.data.parser

import com.google.gson.Gson
import com.hyu.webdataviewer.data.data.AmiiboData
import com.hyu.webdataviewer.data.data.AmiiboDatas
import io.reactivex.Observable

class AmiiboDataParser : IDataParser<String, List<AmiiboData>> {
    override fun parse(dataObservable : Observable<String>): Observable<List<AmiiboData>> {
        return dataObservable.map {
            Gson().fromJson(it , AmiiboDatas::class.java).amiiboList
        }
    }

    override fun release() {

    }
}