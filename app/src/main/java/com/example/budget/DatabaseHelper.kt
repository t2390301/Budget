package com.example.budget

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.budget.model.constants.BANKSENTITY
import com.example.budget.model.constants.BUDGETGROUPS
import com.example.budget.model.database.AppDatabase
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_1_2
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_2_3
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_3_4
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_4_5
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
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .build()


        CoroutineScope(Dispatchers.IO).launch {

           /* appDataBase!!.bankAccountDao().deleteAll()      //только для тестов
            appDataBase!!.sellerDao().deleteAll()
            appDataBase!!.budgetEntryEntityDao().deleteAll()*/
            val banks = appDataBase!!.bankDao().getAll()
            if (banks.isEmpty() or (banks.filter{  it.smsAddress.equals("Tinkoff") }.first().bankImage == null )) {
                for (bank in BANKSENTITY) {
                    appDataBase!!.bankDao().update(bank)
                }
                Log.i(DB_NAME, "initDatabase: In BANKS")
            }
            if (appDataBase!!.budgetEntryEntityDao().getAll().isEmpty()) {
                for (budgetGroupEntity in BUDGETGROUPS) {
                    appDataBase!!.budgetGroupEntityDao().insert(
                        budgetGroupEntity
                    )
                }
            }
        }
    }

    fun getSmsDataDao(): SmsDataDao = appDataBase!!.smsDataDao()

    fun getBudgetEntryEntityDao(): BudgetEntryDao = appDataBase!!.budgetEntryEntityDao()

    fun getBudgetGroupEntityDao(): BudgetGroupDao = appDataBase!!.budgetGroupEntityDao()

    fun getBanksDAO(): BankDao = appDataBase!!.bankDao()
    fun getBankAccountDao(): BankAccountDao = appDataBase!!.bankAccountDao()
    fun getSellerDao(): SellerDao = appDataBase!!.sellerDao()
    fun getPlanningNoteDao() = appDataBase!!.getPlanningNoteDao()
    fun getCombainBudgetEntriesDao()=appDataBase!!.combainTableDao()




}