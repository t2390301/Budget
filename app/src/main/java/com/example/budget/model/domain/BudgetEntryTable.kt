package com.example.budget.model.domain

data class BudgetEntryTable(
    val date: Long,
    val bankName: String,
    val cardPan: String,
    val operationAmount: Double,
    val sellerName: String,
    val budgetGroupName: String,
    val bankImageId: Int,
    val operationType: OperationType
)
