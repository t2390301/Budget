package com.example.budget

import android.app.Application


class App : Application() {
    companion object {
        lateinit var app: App
    }
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()
        app = applicationContext as App
        initDatabase()
    }

    fun getDatabaseHelper(): DatabaseHelper {
        return databaseHelper
    }

    private fun initDatabase() {
        databaseHelper = DatabaseHelper()
        databaseHelper.initDatabase(this)
    }
}
