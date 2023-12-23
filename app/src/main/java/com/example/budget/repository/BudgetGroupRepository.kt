package com.example.budget.repository

import com.example.budget.model.utils.DatabaseHelper
import com.example.budget.model.database.entity.toBudgetGroup
import com.example.budget.model.database.entity.toBudgetGroupEntity
import com.example.budget.model.domain.BudgetGroup

class BudgetGroupRepository(databaseHelper: DatabaseHelper) {

    private val budgetGroupDao = databaseHelper.getBudgetGroupEntityDao()

    suspend fun getBudgetGroups(): List<BudgetGroup> {
        return budgetGroupDao.getAll().map {
            it.toBudgetGroup()
        }
    }

    suspend fun insertBudgetGroup(budgetGroup: BudgetGroup) =
        budgetGroupDao.insert(budgetGroup.toBudgetGroupEntity())
}