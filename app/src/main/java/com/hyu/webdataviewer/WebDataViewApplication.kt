package com.hyu.webdataviewer

import android.app.Application
import com.hyu.webdataviewer.di.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WebDataViewApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
//            androidLogger(Level.DEBUG)
            androidContext(this@WebDataViewApplication)
            modules(baseModule, amiiboModule, previewModule, otherModule)
        }
    }
}

