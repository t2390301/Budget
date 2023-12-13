package com.example.budget.model.domain

import java.util.Date

data class BudgetEntry(
    var id: Long,
    var date: Date,
    var operationType: OperationType,
    var bankAccountId: Long,
    val note: String = "",
    var operationAmount: Double,
    var sellerId: Long,
)