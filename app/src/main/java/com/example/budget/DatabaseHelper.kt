package com.example.budget

import android.app.Application
import androidx.room.Room
import com.example.budget.model.constants.BANKSENTITY
import com.example.budget.model.constants.BUDGETGROUPS
import com.example.budget.model.database.AppDatabase
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_1_2
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_2_3
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_3_4
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_4_5
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_5_6
import com.example.budget.model.database.AppDatabase.Companion.MIGRATION_6_7
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

    private val TAG = "DatabaseHelper"
    private var appDataBase: AppDatabase? = null

    fun initDatabase(context: Application) {

        appDataBase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .addMigrations(MIGRATION_5_6)
                .addMigrations(MIGRATION_6_7)
                .build()


        CoroutineScope(Dispatchers.IO).launch {

            /*  appDataBase!!.bankAccountDao().deleteAll()      //только для тестов
              appDataBase!!.sellerDao().deleteAll()
              appDataBase!!.budgetEntryEntityDao().deleteAll()
              appDataBase!!.smsDataDao().deleteNull()*/

            var banks = appDataBase!!.bankDao().getAll()

            if (banks.isEmpty()) {
                for (bank in BANKSENTITY) {
                    appDataBase!!.bankDao().insert(bank)
                    banks = appDataBase!!.bankDao().getAll()
                }
            } else if (banks.filter { it.smsAddress.equals("AlfaBank") }
                    .first().bankImage == null) {
                for (bank in BANKSENTITY) {
                    appDataBase!!.bankDao().update(bank)
                }
            }

            val budgetGroups = appDataBase!!.budgetGroupEntityDao().getAll()
            if (budgetGroups.isEmpty()) {
                for (budgetGroupEntity in BUDGETGROUPS) {
                    appDataBase!!.budgetGroupEntityDao().insert(
                        budgetGroupEntity
                    )
                }
            } else if (budgetGroups.filter { it.name.equals("ПРОДУКТЫ") }.first().iconResId == 0)
            {
                for (budgetGroupEntity in BUDGETGROUPS) {
                    appDataBase!!.budgetGroupEntityDao().update(
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
    fun getCombainBudgetEntriesDao() = appDataBase!!.combainTableDao()
    fun getBudgetGroupWithAmountDao() = appDataBase!!.budgetGroupWithAmountDao()




}