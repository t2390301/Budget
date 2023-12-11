package com.example.budget.model.utils

import android.util.Log
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import java.util.Date
import java.util.regex.Pattern


class SmsDataMapper(var sellers: List<Seller>, var bankAccounts: List<BankAccount>) {
    companion object {
        const val CURRENCY = "RUB"
        const val SPACE = " "
    }

    fun convertSMSToBudgetEntry(sms: SmsData): BudgetEntry {

        val budgetEntry = BudgetEntry(0, Date(0), OperationType.EXPENSE, 0, "", 0.0, 0)

        for (card in bankAccounts) {
            if (sms.body.contains(card.cardPan, true)) {
                with(budgetEntry) {

                    id = sms.date                      //Вряд ли в одну миллисекунду придут два SMS
                    date = Date(sms.date)
                    bankAccountId = card.id

                    val stringAfterCardPan = sms.body.substringAfter("${card.cardPan}.")

                    val pattern = Pattern.compile("\\d+\\.?\\d*")
                    val matcher = pattern.matcher(stringAfterCardPan)
                    if (matcher.find()) {
                        Log.i(TAG, "convertSMSToBudgetEntry: matcher1 = ${matcher.group()}")
                        operationAmount = matcher.group().toDouble()
                    }
                    if (matcher.find()) {
                        Log.i(TAG, "convertSMSToBudgetEntry: matcher2 = ${matcher.group()}")
                        card.balance = matcher.group().toDouble()
                    }


                    for (seller in sellers) {
                        if (sms.body.contains(seller.name)) {
                            sellerId = seller.id
                            operationType = OperationType.EXPENSE
                        }
                    }
                }
            }
        }

        return budgetEntry
    }

    fun updateRules(newSellers: List<Seller>) {
        sellers = newSellers
    }

    fun updateCars(newCards: List<BankAccount>) {
        bankAccounts = newCards
    }
}