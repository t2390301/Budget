package com.example.budget.model.data

import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.Seller


class SmsDataMapper(var sellers: List<Seller>, var cards: List<BankAccount>) {
    companion object {
        const val CURRENCY = "RUB"
        const val SPACE = " "
    }
    /*
        fun convertSMSToBudgetEntry(sms: SmsData): BudgetEntry {
            var id: Long = 0
            var date: Date = Date(0)
            var operationType: OperationType = OperationType.EXPENSE
            var operationAmount: Double = 0.0
            var transactionSource: String = ""
            var bankAccountId: Long = 0
            var balance: Double = 0.0



            for (card in cards) {
                if (sms.body.contains(card.cardPan, true)) {
                    bankAccountId = card.id
                    id =
                        sms.date                      //Вряд ли в одну миллисекунду придут два SMS
                    date = Date(sms.date)
                    var balanceStr = sms.body.substringBeforeLast(CURRENCY, "").trim()

                    balanceStr = balanceStr.substringAfterLast(SPACE, "").trim()
                    if (balanceStr.length > 0) {
                        Log.i(TAG, "convertSMSToBudgetEntry: balanceSTR = $balanceStr")
                        balanceStr.toDouble().let {
                            card.balance = it
                        }
                    }

                }
                for (seller in sellers) {
                    if (sms.body.contains(seller.name)) {
                        transactionSource = seller.transactionSource
                        operationType = OperationType.EXPENSE
                    }
                }
            }

            return BudgetEntry(
                id = id,
                date = date,
                operationType = operationType,
                operationAmount = operationAmount,
                sellerId =12,
                bankAccountId = bankAccountId
            )
        }

        fun updateRules(newSellers: List<Seller>) {
            sellers = newSellers
        }

        fun updateCars(newCards: List<BankAccount>) {
            cards = newCards
        }*/
}