package com.example.budget

import android.app.Application
import timber.log.Timber


class App : Application() {
    companion object {
        lateinit var app: App
    }

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        app = applicationContext as App
        initDatabase()
    }

    fun getDatabaseHelper(): DatabaseHelper {

    //    initDatabase()

        return databaseHelper
    }

    private fun initDatabase() {
        databaseHelper = DatabaseHelper()
        databaseHelper.initDatabase(this)
    }
}
