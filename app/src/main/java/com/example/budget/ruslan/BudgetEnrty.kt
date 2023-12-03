package com.example.budget.ruslan

import java.util.*

data class BudgetEntry(
    var id: Long = 0L,
    var smsId: Long? = 0L,
    var date: Date = Date(0),
    var operationType: OperationType = OperationType.EXPENSE,
    var operationAmount: Double = 0.0,
    var transactionSource: TransactionSource = TransactionSource.ПРОДУКТЫ,
    var note: String = "",
    var cardPan: String = ""
)
