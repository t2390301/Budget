package com.example.budget.model.domain

//Названия банков и номер отправителя
data class Bank(
    val id: Long,
    val name: String,
    val smsAddress: String
)