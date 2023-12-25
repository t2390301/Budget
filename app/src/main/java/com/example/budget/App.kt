package com.example.budget

import android.app.Application
import com.example.budget.di.application
import com.example.budget.di.budgetEntry
import com.example.budget.di.exportAndBackup
import com.example.budget.di.planningNote
import com.example.budget.model.utils.AppLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {
    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
        app = applicationContext as App
        startKoin {
            androidLogger()
            androidContext(app)
            modules(listOf(application, planningNote, budgetEntry, exportAndBackup))
        }
    }

   /* fun getDatabaseHelper(): DatabaseHelper {
        return databaseHelper
    }*/

    /*private fun initDatabase() {
        databaseHelper = DatabaseHelper()
        databaseHelper.initDatabase(this)
    }*/

}
