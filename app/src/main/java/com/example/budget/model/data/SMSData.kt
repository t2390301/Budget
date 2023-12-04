package com.example.budget.model.data

import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log

const val TAG = "SMSDataMapper"

class SMSData(private val applicationContext: Context) {

     fun readAllSMS(): List<SMS?>? {
         val cr = applicationContext.contentResolver
         val cursor = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null)
         cursor?.let {
             return convertCursorToSMS(it)
         }
         return null
     }

     fun readSMSAfterDate(date: Long): List<SMS?>? {
         val cursor = applicationContext.contentResolver.query(
             Telephony.Sms.CONTENT_URI, null,
             "${Telephony.Sms.DATE} > ?", arrayOf(date.toString()), null
         )
         cursor?.let {
             return convertCursorToSMS(it)
         }
         return null
     }

     fun readSMSFromSender(sender: String): List<SMS?>? {
         val cursor = applicationContext.contentResolver.query(
             Telephony.Sms.CONTENT_URI, null,
             "${Telephony.Sms.ADDRESS} = ?", arrayOf(sender), null
         )
         cursor?.let {
             return convertCursorToSMS(it)
         }
         return null
     }

     private fun convertCursorToSMS(cursor: Cursor): List<SMS?>? {
         var smsList = mutableListOf<SMS>()
         if (cursor.moveToFirst() == true) {
             while (!cursor.isAfterLast) {
                 val smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                 val number: String =
                     cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                 val body: String =
                     cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                 Log.i(TAG, "readSMS:  $number : $body")
                 smsList.add(SMS(smsDate.toLong(), number, body))
                 cursor.moveToNext()
             }
             cursor.close()
             return smsList
         }
         return null
     }
}