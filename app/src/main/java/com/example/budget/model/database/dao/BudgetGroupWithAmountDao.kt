package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.budget.model.domain.BudgetGroupWithAmount

@Dao
interface BudgetGroupWithAmountDao {

    @Query("SELECT budget_group_table.id, budget_group_table.name, budget_group_table.description, " +
            "budget_group_table.iconResId, SUM(budget_entry_table.operationAmount) as totalAmount " +
            "FROM budget_group_table  LEFT  JOIN seller_table ON budget_group_table.id =seller_table.budgetGroupId " +
            "LEFT JOIN budget_entry_table  ON seller_table.id = budget_entry_table.sellerId GROUP BY budget_group_table.name")
    suspend fun getAll(): List<BudgetGroupWithAmount>
}