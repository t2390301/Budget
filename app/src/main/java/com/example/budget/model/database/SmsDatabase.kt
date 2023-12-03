package com.example.budget.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budget.model.database.dao.BudgetEntryEntityDao
import com.example.budget.model.database.dao.BudgetGroupEntityDao
import com.example.budget.model.database.dao.SmsDataDao

@Database(
    entities = [SmsDataEntity::class, BudgetGroupEntity::class, BudgetEntryEntity::class],
    version = 1
)
abstract class SmsDatabase : RoomDatabase() {
    abstract fun smsDataDao(): SmsDataDao
    abstract fun budgetGroupEntityDao(): BudgetGroupEntityDao
    abstract fun budgetEntryEntityDao(): BudgetEntryEntityDao
}




