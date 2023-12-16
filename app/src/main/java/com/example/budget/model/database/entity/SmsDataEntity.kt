package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.domain.SmsData

@Entity(tableName = "sms_data_table")
data class SmsDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long,
    val sender: String,
    val body: String,
    var isCashed: Boolean,
    var bankAccountFound: Boolean,
    var sellerFound: Boolean,
)

fun SmsDataEntity.toSmsData(): SmsData {
    return SmsData(
        id, date, sender, body, isCashed, bankAccountFound, sellerFound
    )
}

fun SmsData.toSmsDataEntity(): SmsDataEntity {
    return SmsDataEntity(
        id, date, sender, body, isCashed, bankAccountFound, sellerFound
    )
}