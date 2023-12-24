package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_data_table")
data class SmsDataEntity(
    @PrimaryKey(autoGenerate = true)
    var date: Long,
    var sender: String,
    var body: String,
    var isCashed: Boolean,

    )
