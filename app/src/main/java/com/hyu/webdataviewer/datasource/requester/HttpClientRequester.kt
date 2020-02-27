package com.hyu.webdataviewer.datasource.requester

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class HttpClientRequester : IDataRequester<String>{


    override fun request(path: String): Observable<String> {
        return Observable.create<String> {
            val url = URL(path)

            val connection = url.openConnection()
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.getInputStream(), "UTF-8"))

            val buffer = StringBuffer()
            var readData : String? = null

            while (true) {
                reader.readLine()?.let{
                    buffer.append(readData)
                } ?: break
            }

            it.onNext(buffer.toString())
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun release() {

    }

}