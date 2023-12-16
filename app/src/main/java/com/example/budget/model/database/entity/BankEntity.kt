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
    val id: Long = 0,
    val name: String,
    val smsAddress: String,
    // val bankAccountId: Long
)

fun BankEntity.toBank(): Bank {
    return Bank(
        id, name, smsAddress
    )
}

fun Bank.toBankEntity(): BankEntity {
    return BankEntity(
        id, name, smsAddress
    )
}