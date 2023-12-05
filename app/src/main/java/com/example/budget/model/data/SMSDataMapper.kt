package com.example.budget.model.data

import android.util.Log
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.OperationType
import java.util.Date


class SMSDataMapper(var sellers: List<Seller>, var cards: List<BankCard>) {
    companion object {
        const val CURRENCY = "RUB"
        const val SPACE = " "
    }

    fun convertSMSToBudgetEntry(sms: SMS): BudgetEntry {
        val budgetEntry = BudgetEntry()

        for (card in cards) {
            if (sms.body.contains(card.cardPan, true)) {
                budgetEntry.cardPan = card.cardPan
                budgetEntry.id =
                    sms.date                      //Вряд ли в одну миллисекунду придут два SMS
                budgetEntry.smsId = sms.date
                budgetEntry.date = Date(sms.date)
                var stringAfterCardPan = sms.body.substringAfter("${card.cardPan}.")
                val pattern = Pattern.compile("\\d+\\.?\\d*")
                val matcher = pattern.matcher(stringAfterCardPan)
                if (matcher.find()) {
                    Log.i(TAG, "convertSMSToBudgetEntry: matcher1 = ${matcher.group()}")
                    budgetEntry.operationAmount = matcher.group().toDouble()
                }
                if (matcher.find()) {
                    Log.i(TAG, "convertSMSToBudgetEntry: matcher2 = ${matcher.group()}")
                    card.balance = matcher.group().toDouble()
                }

            }
            for (seller in sellers) {
                if (sms.body.contains(seller.name)) {
                    budgetEntry.transactionSource = seller.transactionSource
                    budgetEntry.operationType = OperationType.EXPENSE
                }
            }
        }

        return budgetEntry
    }

    fun updateRules(newSellers: List<Seller>) {
        sellers = newSellers
    }

    fun updateCars(newCards: List<BankCard>) {
        cards = newCards
    }
}