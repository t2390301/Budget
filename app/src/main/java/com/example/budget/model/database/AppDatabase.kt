package com.example.budget.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budget.model.database.converters.DateConverter
import com.example.budget.model.database.converters.OperationTypeConverter
import com.example.budget.model.database.converters.StringListConverter
import com.example.budget.model.database.dao.BankAccountDao
import com.example.budget.model.database.dao.BankDao
import com.example.budget.model.database.dao.BudgetEntryDao
import com.example.budget.model.database.dao.BudgetGroupDao
import com.example.budget.model.database.dao.CombainTableDao
import com.example.budget.model.database.dao.SellerDao
import com.example.budget.model.database.dao.SmsDataDao
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.SmsDataEntity

@Database(
    entities = [
        SmsDataEntity::class,
        BudgetGroupEntity::class,
        BudgetEntryEntity::class,
        BankAccountEntity::class,
        BankEntity::class],
    version = 1
)
@TypeConverters(
    OperationTypeConverter::class,
    DateConverter::class,
    StringListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smsDataDao(): SmsDataDao
    abstract fun budgetGroupEntityDao(): BudgetGroupDao
    abstract fun budgetEntryEntityDao(): BudgetEntryDao
    abstract fun bankDao(): BankDao

    abstract fun sellerDao(): SellerDao
    abstract fun bankAccountDao(): BankAccountDao
    abstract fun combainTableDao(): CombainTableDao
}




