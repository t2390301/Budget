package com.example.budget.model.domain

import com.example.budget.model.constants.BudgetGroupEnum

data class Seller(
    //val id: Long,
    val name: String,
    val budgetGroupName: BudgetGroupEnum
)
