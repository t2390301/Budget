package com.example.budget

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.budget.model.constants.BANKSENTITY
import com.example.budget.model.constants.BUDGETGROUPS
import com.example.budget.model.database.AppDatabase
import com.example.budget.model.database.dao.BankAccountDao
import com.example.budget.model.database.dao.BankDao
import com.example.budget.model.database.dao.BudgetEntryDao
import com.example.budget.model.database.dao.BudgetGroupDao
import com.example.budget.model.database.dao.SellerDao
import com.example.budget.model.database.dao.SmsDataDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val DB_NAME = "AppDatabase.db"

class DatabaseHelper {

    private var appDataBase: AppDatabase? = null

    fun initDatabase(context: Application) {
        appDataBase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()


        CoroutineScope(Dispatchers.IO).launch {
            for (bank in BANKSENTITY) {
                appDataBase!!.bankDao().insert(bank)
            }
            Log.i(DB_NAME, "initDatabase: In BANKS")
            for (budgetGroupEntity in BUDGETGROUPS) {
                appDataBase!!.budgetGroupEntityDao().insert(
                   budgetGroupEntity
                )
            }
        }
    }

    fun getSmsDataDao(): SmsDataDao = appDataBase!!.smsDataDao()

    fun getBudgetEntryDao(): BudgetEntryDao = appDataBase!!.budgetEntryEntityDao()

    fun getBudgetGroupDao(): BudgetGroupDao = appDataBase!!.budgetGroupEntityDao()

    fun getBanksDAO(): BankDao = appDataBase!!.bankDao()
    fun getBankAccountDao(): BankAccountDao = appDataBase!!.bankAccountDao()
    fun getSellerDao(): SellerDao = appDataBase!!.sellerDao()


}