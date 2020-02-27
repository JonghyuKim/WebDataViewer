package com.hyu.webdataviewer.domain.usecase

import com.hyu.webdataviewer.domain.datasource.IDataRepository
import com.hyu.webdataviewer.domain.model.AmiiboModel
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetAmiiboModelUseCase : IUseCase<List<AmiiboModel>>, KoinComponent {

    private val repository by inject<IDataRepository<AmiiboModel>>()

    override fun excute(): Single<List<AmiiboModel>> {
        return repository.loadData()
        }

    fun release() {
        repository.release()
    }
}