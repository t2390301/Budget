package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_data_table")
data class SmsDataEntity(
    @PrimaryKey(autoGenerate = true)
    val date: Long,
    val sender: String,
    val body: String,
    var isCashed: Boolean,
    /*var bankAccountFound: Boolean,
    var sellerFound: Boolean,*/
)
