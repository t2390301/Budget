package com.example.budget

import android.app.Application
import com.example.budget.di.Di
import com.example.budget.model.utils.AppLogger
import com.example.budget.model.utils.DatabaseHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {
    companion object {
        lateinit var app: App
    }

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
        app = applicationContext as App
        initDatabase()
        startKoin {
            androidLogger()
            androidContext(app)
            modules(listOf(Di.application, Di.mainScreen))
        }
    }

    fun getDatabaseHelper(): DatabaseHelper {
        return databaseHelper
    }

    private fun initDatabase() {
        databaseHelper = DatabaseHelper()
        databaseHelper.initDatabase(this)
    }
    //TODO delete dbhelper
}
