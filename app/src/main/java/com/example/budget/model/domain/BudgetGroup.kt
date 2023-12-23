package com.example.budget.model.domain

import com.example.budget.model.constants.BudgetGroupEnum

//Статья расходов
data class BudgetGroup(
    //val id: Long,
    val name: BudgetGroupEnum,  //название статьи
    val description: String, //описание
    val iconResId: Long //иконка
)