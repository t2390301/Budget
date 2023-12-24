package com.example.budget.repository

import android.content.Context
import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsReader

class SMSRepository(appContext: Context) {
    companion object {
        const val TAG = "SMSRepository"
    }

    private val smsReader = SmsReader(appContext)

    suspend fun readAllSMS(): List<SmsData?>? =
        smsReader.readAllSMS()

    suspend fun readSMSafterDate(date: Long): List<SmsData>? =
        smsReader.readSMSAfterDate(date)


    suspend fun readSMSFromSender(sender: String): List<SmsData>? =
        smsReader.readSMSFromSender(sender)

}