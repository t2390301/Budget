package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.domain.OperationType
import java.util.Date

@Entity(
    tableName = "budget_entry_table"
)
data class BudgetEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val operationType: OperationType,
    val bankAccountId: Long,
    val note: String = "",
    val operationAmount: Double,
    val sellerId: Long,
)
/*

fun BudgetEntryEntity.toBudgetEntry(): BudgetEntry {
    return BudgetEntry(
        id, date, operationType, bankAccountId, note, operationAmount, sellerId
    )
}

fun BudgetEntry.toBudgetEntryEntity(): BudgetEntryEntity {
    return BudgetEntryEntity(
        id, date, operationType, bankAccountId, note, operationAmount, sellerId
    )
}*/
