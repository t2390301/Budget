package com.example.budget.model.utils

import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import com.example.budget.model.domain.SmsData

const val TAG = "SMSDataMapper"

class SmsReader(private val applicationContext: Context) {

     suspend fun readAllSMS(): List<SmsData?>? {
         val cr = applicationContext.contentResolver
         val cursor = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null)
         cursor?.let {
             return convertCursorToSMS(it)
         }
         return null
     }

     suspend fun readSMSAfterDate(date: Long): List<SmsData>? {
         val cursor = applicationContext.contentResolver.query(
             Telephony.Sms.CONTENT_URI, null,
             "${Telephony.Sms.DATE} > ?", arrayOf(date.toString()), null
         )
         cursor?.let {
             return convertCursorToSMS(it)
         }
         return null
     }

     suspend fun readSMSFromSender(sender: String): List<SmsData>? {
         val cursor = applicationContext.contentResolver.query(
             Telephony.Sms.CONTENT_URI, null,
             "${Telephony.Sms.ADDRESS} = ?", arrayOf(sender), null
         )
         cursor?.let {
             return convertCursorToSMS(it)
         }
         return null
     }

     private suspend fun convertCursorToSMS(cursor: Cursor): List<SmsData>? {
         val smsList = mutableListOf<SmsData>()
         Log.i(TAG, "convertCursorToSMS:  readSMS")
         if (cursor.moveToFirst() == true) {
             while (!cursor.isAfterLast) {
                 val smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                 val number: String =
                     cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                 val body: String =
                     cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))

                 //Log.i(TAG, "readSMS:  $number : $body")

                 smsList.add(SmsData(smsDate.toLong(), number, body, false, null))
                 cursor.moveToNext()
             }
             cursor.close()
             return smsList
         }
         return null
     }
}