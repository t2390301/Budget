package com.example.budget.model.domain

//Названия банков и номер отправителя
data class Bank(
    val id: Long = 0,
    val name: String,
    val smsAddress: String
)