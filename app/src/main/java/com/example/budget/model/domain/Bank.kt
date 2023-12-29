package com.example.budget.model.domain

//Названия банков и номер отправителя
data class Bank(
    val id: Long,
    val name: String,
    val smsAddress: String,
    var operationTypeEXPENSERegex: String,
    var operationTypeINCOMERegex: String,
    var cardPanRegex: String,
    var sellerNameRegex: String,
    var operationAmountRegex: String,
    var balanceRegex: String,
    val bankImage : Int?
)