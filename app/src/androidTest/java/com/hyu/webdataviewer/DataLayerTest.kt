package com.hyu.webdataviewer


import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.hyu.webdataviewer.data.data.AmiiboData
import com.hyu.webdataviewer.data.parser.AmiiboDataParser
import com.hyu.webdataviewer.data.parser.IDataParser
import com.hyu.webdataviewer.data.requester.HttpClientRequester
import com.hyu.webdataviewer.data.requester.IDataRequester
import com.hyu.webdataviewer.data.requester.JsonFileRequester
import com.hyu.webdataviewer.util.log.HLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import java.io.IOException
import okhttp3.RequestBody
import okio.Buffer


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DataLayerTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.hyu.webdataviewer", appContext.packageName)
    }

    @Test
    fun amiiboRepositoryTest(){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://www.amiiboapi.com/api/amiibo")
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                System.out.println("amiibo error!! : ${e.stackTrace}")

            }

            override fun onResponse(call: Call, response: Response) {
                System.out.println("amiibo : ${response}")
                System.out.println("amiibo : ${response.body()?.string()}")

            }

        })

        Thread.sleep(20000)

    }

    @Throws(IOException::class)
    fun requestBodyToString(requestBody: RequestBody): String {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    }

    @Test
    fun amiiboRealWebRequesterTest() {
        val koin = koinApplication {
            printLogger(Level.DEBUG)
            androidContext(InstrumentationRegistry.getTargetContext())
            modules(
                module {
                    factory { HttpClientRequester() as IDataRequester<String> }
                    factory { AmiiboDataParser() as IDataParser<String, List<AmiiboData>> }
                }
            )
        }.koin

        val requester = koin.get<IDataRequester<String>>()
        val parser = koin.get<IDataParser<String, List<AmiiboData>>>()

        requester.request("https://www.amiiboapi.com/api/amiibo")
            .subscribeOn(Schedulers.io())
            .subscribe {
                HLog.d("data : $it")

            }

        Thread.sleep(20000)
    }

    @Test
    fun amiiboDummyFileDataRequesterTest() {
        val koin = koinApplication {
            printLogger(Level.DEBUG)
            androidContext(InstrumentationRegistry.getTargetContext())
            modules(
                module {
                    factory { JsonFileRequester(get()) as IDataRequester<String> }
                    factory { AmiiboDataParser() as IDataParser<String, List<AmiiboData>> }
                }
            )
        }.koin

        val requester = koin.get<IDataRequester<String>>()
        val parser = koin.get<IDataParser<String, List<AmiiboData>>>()

        parser.parse(requester.request("amiibo_dummy.json"))
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                HLog.d("data : $it")
            }.blockingSubscribe()
    }
}