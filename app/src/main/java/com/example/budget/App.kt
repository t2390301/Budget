package com.example.budget

import android.app.Application
import com.example.budget.di.application
import com.example.budget.di.repository
import com.example.budget.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {
    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        app = applicationContext as App
        startKoin {
            androidLogger()
            androidContext(app)
            modules(listOf(application, viewModels, repository))
        }
    }
}
