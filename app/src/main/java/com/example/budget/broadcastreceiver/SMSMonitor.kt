package com.example.budget.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

const val SMS_SENDER = "sms_sender"
const val SMS_DATE = "sms_date"
const val SMS_BODY = "sms_body"

class SMSMonitor : BroadcastReceiver() {

    companion object {
        const val TAG = "SMSMonitor"
        const val ACTION = "android.provider.Telephony.SMS_RECEIVED"

    }

    var smsFrom: String = ""

    override fun onReceive(context: Context, intent: Intent) {
        if (intent != null && intent.action != null && ACTION.compareTo(
                intent.action!!,
                ignoreCase = true
            ) == 0
        ) {
            CoroutineScope(SupervisorJob()).launch {

                val pduArray = intent.extras!!["pdus"] as Array<Any>?
                val messages = arrayOfNulls<SmsMessage>(
                    pduArray!!.size
                )
                for (i in 0..<pduArray.size) {
                    messages[i] = SmsMessage.createFromPdu(pduArray[i] as ByteArray)
                }

                messages[0]?.displayOriginatingAddress?.let { smsFrom = it }


                val bodyText = StringBuilder()
                for (message in messages) {
                    bodyText.append(message?.messageBody)
                }
                val smsBody = bodyText.toString()

                var intent = Intent("com.example.budget")
                intent.putExtra(SMS_SENDER, smsFrom)
                intent.putExtra(SMS_BODY, smsBody)
                intent.putExtra(SMS_DATE, messages[0]?.getTimestampMillis())
                context.sendBroadcast(intent)

                /*intent = Intent(context,SmsService::class.java)
                intent.putExtra(SMS_SENDER, smsFrom)
                intent.putExtra(SMS_BODY, smsBody)
                intent.putExtra(SMS_DATE, messages[0]?.getTimestampMillis())
                context.startService(intent)*/
                abortBroadcast()


            }
        }
    }
}


