package com.example.budget.model.domain

//меню добавления счета
data class BankAccount(
     val id: Long,
    val cardPan: String, //номер карты
    // val bankId: Long,
    val bankSMSAddress: String,
    var cardType: CardType, //кредитная дебетовая
    var cardLimit: Double, //доступный лимит
    var balance: Double,
    val bankImageId: Int?
)
