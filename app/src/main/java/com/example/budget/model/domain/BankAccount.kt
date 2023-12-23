package com.example.budget.model.domain

//меню добавления счета
data class BankAccount(
    val id: Long = 0,
    val cardPan: String, //номер карты
    //val bankId: Long,
    val bankSMSAddress: String,
    val cardType: CardType, //кредитная дебетовая
    val cardLimit: Double, //доступный лимит
    var balance: Double,
)
