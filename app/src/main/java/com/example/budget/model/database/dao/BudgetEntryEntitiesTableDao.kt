package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.budget.model.domain.BudgetEntryTable

@Dao
interface BudgetEntryEntitiesTableDao {
    @Query(
        "SELECT budget_entry_table.date, bank_table.name as bankName, bank_account_table.cardPan, " +
                "budget_entry_table.operationAmount, " +
                "seller_table.name as sellerName, budget_group_table.name as budgetGroupName, " +
                "bank_table.bankImage as bankImageId, " +
                "budget_entry_table.operationType as operationType " +
                "FROM budget_entry_table " +
                "INNER JOIN bank_account_table ON budget_entry_table.bankAccountId = bank_account_table.id " +
                "INNER JOIN bank_table ON bank_account_table.bankId = bank_table.id" +
                " JOIN seller_table  ON budget_entry_table.sellerId = seller_table.id " +
                "INNER JOIN budget_group_table ON seller_table.budgetGroupId = budget_group_table.id"
    )
    suspend fun getAll(): List<BudgetEntryTable>
}