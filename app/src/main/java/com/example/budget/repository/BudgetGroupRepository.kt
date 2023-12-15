package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toBudgetGroup
import com.example.budget.model.database.entity.toBudgetGroupEntity
import com.example.budget.model.domain.BudgetGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BudgetGroupRepository(databaseHelper: DatabaseHelper) {

    private val budgetGroupDao = databaseHelper.getBudgetGroupDao()

    suspend fun getBudgetGroups(): List<BudgetGroup> {
        return withContext(Dispatchers.IO) {
            budgetGroupDao.getAll().map {
                it.toBudgetGroup()
            }
        }
    }

    suspend fun insertBudgetGroup(budgetGroup: BudgetGroup) =
        budgetGroupDao.insert(budgetGroup.toBudgetGroupEntity())
}