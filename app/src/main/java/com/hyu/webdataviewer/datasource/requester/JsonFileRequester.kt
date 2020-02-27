package com.hyu.webdataviewer.datasource.requester

import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder


class JsonFileRequester(val context: Context) : IDataRequester<String> {

    private var isReleased = false

    @Throws(FileNotFoundException::class, IOException::class)
    override fun request(path: String): Observable<String> {

        return Observable.create<String> {
            var rd : BufferedReader? = null
            val buffer = StringBuilder()
            try {
                val fis = openFile(context, path)
                val reader = BufferedReader(InputStreamReader(fis))
                while (!isReleased) {
                    reader.readLine()?.run{
                        buffer.append(this)
                    } ?: break
                }
                it.onNext(buffer.toString())
                it.onComplete()
            } finally {
                rd?.close()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun release() {
        isReleased = true
    }

    @Throws(IOException::class)
    private fun openFile(context: Context, filename: String): InputStream {
        return context.resources.assets.open(filename)
    }
}
