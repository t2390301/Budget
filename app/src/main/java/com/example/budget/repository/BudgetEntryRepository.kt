package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toBudgetEntry
import com.example.budget.model.database.entity.toBudgetEntryEntity
import com.example.budget.model.domain.BudgetEntry

class BudgetEntryRepository(databaseHelper: DatabaseHelper) {

    private val budgetEntryDao = databaseHelper.getBudgetEntryDao()

    suspend fun getBudgetEntries(): List<BudgetEntry> = budgetEntryDao.getAll().map {
        it.toBudgetEntry()
    }

    suspend fun insertBudgetEntry(budgetEntry: BudgetEntry) =
        budgetEntryDao.insert(budgetEntry.toBudgetEntryEntity())
}