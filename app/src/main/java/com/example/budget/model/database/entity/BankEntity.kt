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
    val bankAccountId: Long
)