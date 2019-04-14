package com.hyu.webdataviewer.domain.usecase

import io.reactivex.Single

interface IUseCase<T> {
    fun excute() : Single<T>
}