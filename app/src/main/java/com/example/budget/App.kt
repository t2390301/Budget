package com.example.budget

import android.app.Application

class App : Application() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate() {
        super.onCreate()

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