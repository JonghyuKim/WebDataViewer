package com.hyu.webdataviewer.data.requester

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpRequester :IDataRequester<String>{
    override fun request(path: String): Observable<String> {
        return Observable.create<String> {

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(path)
                .build()

            val response = client.newCall(request).execute()
            response.body()?.let{ response ->
                it.onNext(response.string())
            } ?: it.onError(RuntimeException(response.message()))

            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun release() {

    }

}