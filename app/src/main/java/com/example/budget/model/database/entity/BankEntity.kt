package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


//Названия банков и номер отправителя
@Entity(
    tableName = "bank_table")
data class BankEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val smsAddress: String,
    var operationTypeEXPENSERegex: String,
    var operationTypeINCOMERegex: String,
    var cardPanRegex: String,
    var sellerNameRegex: String,
    var operationAmountRegex: String,
    var balanceRegex: String

   // val bankAccountId: Long
)
