package com.example.budget.model.domain

import java.util.Date

data class BudgetEntry(
    val id: Long,
    val date: Date,
    val operationType: OperationType,
    val bankAccountId: Long,
    val note: String = "",
    val operationAmount: Double,
    val sellerId: Long,
)