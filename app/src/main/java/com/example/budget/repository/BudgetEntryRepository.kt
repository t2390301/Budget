package com.example.budget.repository

import com.example.budget.model.utils.DatabaseHelper

class BudgetEntryRepository(databaseHelper: DatabaseHelper) {

    private val budgetEntryDao = databaseHelper.getBudgetEntryEntityDao()

    /*
        suspend fun getBudgetEntries(): List<BudgetEntry> {
            return budgetEntryDao.getAll().map {
                it.toBudgetEntry()
            }
        }

        suspend fun insertBudgetEntry(budgetEntry: BudgetEntry) =
            budgetEntryDao.insert(budgetEntry.toBudgetEntryEntity())*/
}
