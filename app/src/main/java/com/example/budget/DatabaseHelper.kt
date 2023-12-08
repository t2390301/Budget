package com.example.budget

import android.app.Application
import androidx.room.Room
import com.example.budget.model.database.AppDatabase
import com.example.budget.model.database.dao.BudgetEntryDao
import com.example.budget.model.database.dao.BudgetGroupDao
import com.example.budget.model.database.dao.SmsDataDao

private const val DB_NAME = "AppDatabase.db"

class DatabaseHelper {

    private var appDataBase: AppDatabase? = null

    fun initDatabase(context: Application) {
        appDataBase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
    }

    fun getSmsDataDao(): SmsDataDao = appDataBase!!.smsDataDao()

    fun getBudgetEntryEntityDao(): BudgetEntryDao = appDataBase!!.budgetEntryEntityDao()

    fun getBudgetGroupEntityDao(): BudgetGroupDao = appDataBase!!.budgetGroupEntityDao()
}