package com.gturedi.github

import android.app.Application
import com.gturedi.github.di.networkModule
import com.gturedi.github.di.repositoryModule
import com.gturedi.github.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CompApp : Application() {

    companion object {
        var ctx: CompApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@CompApp)
            modules( networkModule, repositoryModule, viewModelModule)
        }
    }
}