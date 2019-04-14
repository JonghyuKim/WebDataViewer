package com.hyu.webdataviewer.domain.usecase

import com.hyu.webdataviewer.data.data.AmiiboData
import com.hyu.webdataviewer.data.repository.IRepository
import com.hyu.webdataviewer.domain.model.AmiiboModel
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetAmiiboModelUseCase : IUseCase<List<AmiiboModel>>, KoinComponent {

    private val repository by inject<IRepository<AmiiboData>>()

    /**
     * 1. get amiibo data from amiiboRepository
     * 2. data convert to AmiiboModel
     */
    override fun excute(): Single<List<AmiiboModel>> {
        return repository.loadData()
            .flatMap {
                Observable.fromIterable(it)
            }
            .map(::convertDataToModel)
            .toList()

        }
    private fun convertDataToModel(data : AmiiboData) : AmiiboModel{
        return AmiiboModel(data.amiiboSeries,data.character,data.gameSeries,data.image,data.name)
    }

    fun release() {
        repository.release()
    }
}