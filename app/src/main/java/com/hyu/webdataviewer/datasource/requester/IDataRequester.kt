package com.hyu.webdataviewer.datasource.requester

import io.reactivex.Observable

interface IDataRequester<T> {


    @Throws(Exception::class)
    fun request(path: String): Observable<T>

    fun release()
}
