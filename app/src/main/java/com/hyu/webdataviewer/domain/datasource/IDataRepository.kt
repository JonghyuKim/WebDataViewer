package com.hyu.webdataviewer.domain.datasource

import io.reactivex.Single

/**
 * Management Data
 */
interface IDataRepository<T> {

    fun loadData() : Single<List<T>>

    fun release()
}
