package com.example.budget.model.domain

import java.util.Date


class BudgetEntry(
    id: Long,
    smsId: Long,
    date: Date,
    operationType: OperationType,
    operationAmount: Double,
    transactionSource: String,
    note: String,
    cardPan: String
)