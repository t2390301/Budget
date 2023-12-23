package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.CardType

@Entity(tableName = "bank_account_table")
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardPan: String, //номер карты
    val bankId: Long,
    val bankSMSAddress: String,
    val cardType: CardType, //кредитная дебетовая
    val cardLimit: Double, //доступный лимит
    var balance: Double,
)

fun BankAccountEntity.toBankAccount(): BankAccount {
    return BankAccount(
        id,
        cardPan,
        bankSMSAddress = "",
        cardType,
        cardLimit,
        balance
    )
}

fun BankAccount.toBankAccountEntity(): BankAccountEntity {
    return BankAccountEntity(
        id,
        cardPan,
        bankId = 0,
        bankSMSAddress,
        cardType,
        cardLimit,
        balance
    )
}
