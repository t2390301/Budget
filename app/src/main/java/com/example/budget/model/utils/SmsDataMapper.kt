package com.example.budget.model.utils

import android.util.Log
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import java.util.Date
import java.util.regex.Pattern


class SmsDataMapper() {
    companion object {
        const val CURRENCY = "RUB"
        const val SPACE = " "
    }


    lateinit var sellers: List<Seller>
    lateinit var bankAccounts: List<BankAccount>

    fun convertSMSToBudgetEntry(sms: SmsData): BudgetEntry? {

        var budgetEntry : BudgetEntry? = null
        sms.bankAccountFound = false
        sms.sellerFound = false
        for (card in bankAccounts) {
            if (sms.body.contains(card.cardPan, true)) {
                sms.bankAccountFound = true
                for (seller in sellers) {
                    if (sms.body.contains(seller.name)) {
                        sms.sellerFound = true
                        val amount = 0.0
                        val stringAfterCardPan = sms.body.substringAfter("${card.cardPan}.")

                        val pattern = Pattern.compile("\\d+\\.?\\d*")
                        val matcher = pattern.matcher(stringAfterCardPan)
                        if (matcher.find()) {
                            Log.i(TAG, "convertSMSToBudgetEntry: matcher1 = ${matcher.group()}")
                            val amount = matcher.group().toDouble()
                        }
                        if (matcher.find()) {
                            Log.i(TAG, "convertSMSToBudgetEntry: matcher2 = ${matcher.group()}")
                            card.balance = matcher.group().toDouble()
                        }
                        budgetEntry = BudgetEntry(
                            id= sms.date,                      //Вряд ли в одну миллисекунду придут два SMS
                            date = Date(sms.date),
                            operationType = OperationType.EXPENSE,
                            bankAccountId = card.id,
                            note = "",
                            operationAmount = amount,
                            sellerId = seller.id
                        )
                    }
                }
            }
        }
        return budgetEntry
    }

    fun updateRules() {

    }

    fun updateSellers(newSellers: List<Seller>) {
        sellers = newSellers
    }

    fun updateBankAccounts(newCards: List<BankAccount>) {
        bankAccounts = newCards
    }
}