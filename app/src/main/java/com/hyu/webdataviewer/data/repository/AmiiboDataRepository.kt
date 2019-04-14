package com.hyu.webdataviewer.data.repository

import com.hyu.webdataviewer.data.data.AmiiboData
import com.hyu.webdataviewer.data.parser.IDataParser
import com.hyu.webdataviewer.data.requester.IDataRequester
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

class AmiiboDataRepository : IRepository<AmiiboData>, KoinComponent{
    private val parser by inject<IDataParser<String, List<AmiiboData>>>()
    private val requester by inject<IDataRequester<String>>()

    private var cacheDataList : List<AmiiboData>? = null

    override fun loadData(): Observable<List<AmiiboData>> {
        return cacheDataList?.let {
            Observable.just(cacheDataList!!)
        } ?: parser.parse(requester.request(PATH_AMIIBO_PATH))
            .doOnNext {
                cacheDataList = it
            }
    }

    override fun release() {
    }

    companion object {
        private val PATH_LOCAL_DUMMY_DATA = "amiibo_dummy.json"
        private val PATH_OPEN_API_DOMAIN = "https://www.amiiboapi.com/"
        private val PATH_AMIIBO_PATH = PATH_OPEN_API_DOMAIN + "api/amiibo"
    }
}