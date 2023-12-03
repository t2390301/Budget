package com.example.budget

import android.app.Application
import androidx.room.Room
import com.example.budget.model.database.SmsDatabase
import com.example.budget.model.database.dao.SmsDataDao

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private var smsDataBase: SmsDatabase? = null
        private var appContext: App? = null
        private const val DB_NAME = "Sms.db"


        fun getSmsDataDao(): SmsDataDao {
            if (smsDataBase == null) {
                if (appContext != null) {
                    smsDataBase =
                        Room.databaseBuilder(appContext!!, SmsDatabase::class.java, DB_NAME)
                            .build()
                } else {
                    throw java.lang.IllegalStateException("Application is null while creating DataBase")
                }
            }
            return smsDataBase!!.smsDataDao()
        }

    }
}