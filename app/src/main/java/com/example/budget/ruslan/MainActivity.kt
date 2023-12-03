package com.ruslanakhmetov.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ruslanakhmetov.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var smsData: SMSData
    lateinit var smsDataMapper: SMSDataMapper

    companion object {
        val sellers = arrayListOf(
            Seller("METRO.SPB", TransactionSource.ТРАНСПОРТ),
            Seller("APTECHNOE", TransactionSource.ПРОДУКТЫ),
            Seller("DIXI-78788D", TransactionSource.ПРОДУКТЫ),
            Seller("BUSHE", TransactionSource.РАЗВЛЕЧЕНИЯ),
            Seller("bilet.nspk", TransactionSource.ТРАНСПОРТ)
        )

        val bankCards = arrayListOf(
            BankCard("Tinkoff", "*0345", 0.0)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        smsData = SMSData(applicationContext)
        smsDataMapper = SMSDataMapper(sellers, bankCards)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        if (checkSelfPermission("android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("android.permission.READ_SMS"), 2)
        } else {

            val smsList = smsData.readSMSAfterDate(1700636623066L)
                ?.let { smslist ->    //readSMSFromSender("HoroshSvyaz")  readSMSAfterDate(1700234258465)
                    for (sms in smslist) {
                        sms?.let {
                            if (sms.sender.equals(bankCards[0].bankName)) {
                                val budgetEntry = smsDataMapper.convertSMSToBudgetEntry(it)
                                Log.i(TAG, "onStart ......`")
                                Log.i(TAG, "smsId: ${budgetEntry?.smsId}  ")
                                Log.i(TAG, "cardPan: ${budgetEntry?.cardPan}  ")
                                Log.i(TAG, "trSource: ${budgetEntry?.transactionSource}  ")
                                Log.i(TAG, "date: ${budgetEntry?.date.toString()}  ")
                                Log.i(TAG, "balance: ${bankCards[0].balance}")
                            }
                        }
                    }
                }
        }

    }
}