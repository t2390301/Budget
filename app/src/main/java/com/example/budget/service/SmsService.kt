package com.example.budget.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.budget.App
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.DBRepository

class SmsService : Service() {

    companion object {
        const val TAG = "SmsService"
        val dbRepository = DBRepository(App.app.getDatabaseHelper())
        val converter = Converters(dbRepository = dbRepository)
        val smsDataMapper = SmsDataMapper(dbRepository)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //CoroutineScope(SupervisorJob()).launch {
            //val banksSMSAddress =
            //    dbRepository.getBankEntities().map { it.smsAddress }.distinct()
            //var smsData = SmsData(0L, "", "", false, null)
           /* intent?.let {
                smsData.date = it.getLongExtra(SMS_DATE, 0)
                smsData.body = it.getStringExtra(SMS_BODY).toString()
                smsData.sender = it.getStringExtra(SMS_SENDER).toString()
            }*/
           // Log.i(TAG, "onStartCommand: smsSender = ${smsData.sender}")

           /* if (smsData.sender in banksSMSAddress) {

                val budgetEntry = smsDataMapper.convertSMSToBudgetEntry(smsData)
                budgetEntry?.let {

                    converter.budgetEntryConverter(it)
                        ?.let { budgetEntryEntity ->
                            dbRepository.insertBudgetEntryEntity(
                                budgetEntryEntity
                            )
                        }
                }
            }*/
            //smsData.isCashed = true
            //Log.i(TAG, "onStartCommand: insert sms ${smsData.body}")
            //dbRepository.insertSMSEntity(converter.smsDataConverter(smsData))
        //}


        return START_STICKY
    }


}