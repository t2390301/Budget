package com.example.budget.model.domain

//Статья расходов
data class BudgetGroup(
    val id: Long = 0,
    val name: String,  //название статьи
    val description: String, //описание
    val iconResId: Long //иконка
)