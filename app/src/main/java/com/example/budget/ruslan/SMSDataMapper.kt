package com.ruslanakhmetov.myapplication

import android.util.Log
import java.util.*


class SMSDataMapper(var sellers: List<Seller>, var cards: List<BankCard>) {

    companion object{
        const val CURRENCY = "RUB"
        const val SPACE = " "
    }

    public fun convertSMSToBudgetEntry(sms: SMS): BudgetEntry? {
        val budgetEntry = BudgetEntry()

        for (card in cards) {
            if (sms.body.contains(card.cardPan, true)) {
                budgetEntry.cardPan = card.cardPan
                budgetEntry.id = sms.date                      //Вряд ли в одну миллисекунду придут два SMS
                budgetEntry.smsId = sms.date
                budgetEntry.date = Date(sms.date)
                var balanceStr = sms.body.substringBeforeLast(CURRENCY, "").trim()

                balanceStr = balanceStr.substringAfterLast(SPACE, "").trim()
                if (balanceStr.length > 0){
                    Log.i(TAG, "convertSMSToBudgetEntry: balanceSTR = $balanceStr")
                    balanceStr.toDouble()?.let {
                        card.balance = it
                    }
                }

            }
            for (seller in sellers){
                if (sms.body.contains(seller.name)){
                   budgetEntry.transactionSource = seller.transactionSource
                   budgetEntry.operationType = OperationType.EXPENSE
                }
            }
        }

        return budgetEntry
    }

    public fun updateRules(newSellers: List<Seller>) {
        sellers = newSellers
    }

    public fun updateCars(newCards: List<BankCard>) {
        cards = newCards
    }
}