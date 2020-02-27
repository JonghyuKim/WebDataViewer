package com.hyu.webdataviewer.datasource.parser

import io.reactivex.Observable

interface IDataParser<T, R> {
    /**
     * Parses jsonData with the desired data.
     */
    fun parse(dataObservable: Observable<T>): Observable<R>

    fun release()
}
