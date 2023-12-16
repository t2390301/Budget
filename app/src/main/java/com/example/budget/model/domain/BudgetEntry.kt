package com.example.budget.model.domain

import com.example.budget.model.constants.BudgetGroupEnum
import java.util.Date

data class BudgetEntry(
    //var id: Long,
    var date: Date,
    var operationType: OperationType,
    //var bankAccountId: Long,
    var cardSPan: String,
    var bankSMSAdress: String,
    val note: String = "",
    var operationAmount: Double,
    //var sellerId: Long,
    val sellerName: String,
    val budgetGroup: BudgetGroupEnum
)