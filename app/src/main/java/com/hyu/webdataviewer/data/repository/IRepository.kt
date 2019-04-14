package com.hyu.webdataviewer.data.repository

import io.reactivex.Observable

/**
 * Management Data
 */
interface IRepository<T> {

    fun loadData() : Observable<List<T>>

    fun release()
}
