package com.hyu.webdataviewer.data.requester

import io.reactivex.Observable

interface IDataRequester<T> {


    @Throws(Exception::class)
    fun request(path: String): Observable<T>

    fun release()
}
