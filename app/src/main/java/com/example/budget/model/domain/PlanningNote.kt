package com.example.budget.model.domain

import com.example.budget.model.constants.BudgetGroupEnum
import java.util.Date

data class PlanningNote(
    val id: Long = 0,
    val date: Date,
    val operationType: OperationType,
    val budgetGroup: BudgetGroupEnum,
    val description: String,
    val value: Long,
)