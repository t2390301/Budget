package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toBudgetGroup
import com.example.budget.model.database.entity.toBudgetGroupEntity
import com.example.budget.model.domain.BudgetGroup

class BudgetGroupRepository(databaseHelper: DatabaseHelper) {

    private val budgetGroupDao = databaseHelper.getBudgetGroupDao()

    suspend fun getBudgetGroups(): List<BudgetGroup> = budgetGroupDao.getAll().map {
        it.toBudgetGroup()
    }

    suspend fun insertBudgetGroup(budgetGroup: BudgetGroup) =
        budgetGroupDao.insert(budgetGroup.toBudgetGroupEntity())
}