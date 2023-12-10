package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.budget.model.domain.CombainBudgetEntry

@Dao
interface CombainTableDao {
    @Query("SELECT budget_entry_table.date, bank_table.name, bank_account_table.cardPan, budget_entry_table.operationAmount, " +
            "seller_table.name, budget_group_table.name " +
            "FROM budget_entry_table " +
            "INNER JOIN bank_account_table ON budget_entry_table.bankAccountId = bank_account_table.id " +
            "INNER JOIN bank_table ON bank_account_table.bankId = bank_table.id" +
            " JOIN seller_table  ON budget_entry_table.sellerId = seller_table.id " +
            "INNER JOIN budget_group_table ON seller_table.budgetGroupId = budget_group_table.id"
    )
    suspend fun getAll(): List<CombainBudgetEntry>
}