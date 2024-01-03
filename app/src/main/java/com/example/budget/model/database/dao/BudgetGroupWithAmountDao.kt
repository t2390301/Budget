package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.budget.model.domain.BudgetGroupWithAmount

@Dao
interface BudgetGroupWithAmountDao {

    @Query("SELECT " +
            "budget_group_table.id, " +
            "budget_group_table.name, budget_group_table.description, budget_group_table.iconResId , " +
            "SUM(budget_entry_table.operationAmount) as totalAmount " +
            "FROM budget_entry_table " +
            "INNER JOIN seller_table ON sellerId = seller_table.id " +
            "INNER JOIN budget_group_table ON seller_table.budgetGroupId = budget_group_table.id " +
            "GROUP BY budget_group_table.name")
    suspend fun getAll(): List<BudgetGroupWithAmount>
}