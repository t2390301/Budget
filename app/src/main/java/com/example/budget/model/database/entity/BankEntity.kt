package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.domain.Bank


//Названия банков и номер отправителя
@Entity(
    tableName = "bank_table"
)
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
)

fun BankEntity.toBank(): Bank {
    return Bank(
        id,
        name,
        smsAddress,
        operationTypeEXPENSERegex,
        operationTypeINCOMERegex,
        cardPanRegex,
        sellerNameRegex,
        operationAmountRegex,
        balanceRegex
    )
}

fun Bank.toBankEntity(): BankEntity {
    return BankEntity(
        id,
        name,
        smsAddress,
        operationTypeEXPENSERegex,
        operationTypeINCOMERegex,
        cardPanRegex,
        sellerNameRegex,
        operationAmountRegex,
        balanceRegex
    )
}