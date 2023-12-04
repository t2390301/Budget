package com.example.budget.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.domain.OperationType
import java.util.Date

@Entity(tableName = "budget_entry_table")
data class BudgetEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val smsId: Long,
    val date: Date,
    val operationType: OperationType,
    val operationAmount: Double,
    val transactionSource: String,
    val note: String,
    val cardPan: String
)