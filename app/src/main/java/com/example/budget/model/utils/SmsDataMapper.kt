package com.example.budget.model.utils

import android.util.Log
import com.example.budget.model.database.converters.bankAccountConverter
import com.example.budget.model.database.converters.sellerConverter
import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.BudgetGroup
import com.example.budget.model.domain.CardType
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.repository.DBRepository
import java.util.Date
import java.util.regex.Pattern


class SmsDataMapper(val dbRepository: DBRepository) {
    companion object {
        const val CURRENCY = "RUB"
        const val SPACE = " "
    }

    lateinit var banks: MutableList<Bank>
    lateinit var sellers: MutableList<Seller>
    lateinit var bankAccounts: MutableList<BankAccount>
    lateinit var budgetGroups: MutableList<BudgetGroup>


    suspend fun convertSMSToBudgetEntry(sms: SmsData): BudgetEntry? {

        var budgetEntry: BudgetEntry? = null
        sms.bankAccountFound = false
        sms.sellerFound = false

        var pattern = Pattern.compile("\\*\\d{4}")
        var matcher = pattern.matcher(sms.body)
        var cardpan = ""
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: cardSpan = ${matcher.group()}")
            cardpan = matcher.group()
        } else {
            return null
        }
        var amount = 0.0
        var balance = 0.0
        val stringAfterCardPan = sms.body.substringAfter("$cardpan.")
        pattern = Pattern.compile("\\d+\\.?\\d*")
        matcher = pattern.matcher(stringAfterCardPan)
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: matcher1 = ${matcher.group()}")
            amount = matcher.group().toDouble()
        }
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: matcher2 = ${matcher.group()}")
            balance = matcher.group().toDouble()
        }

        var bankAccount: BankAccount? = null
        var bankAccountId = 0L
        bankAccounts.filter { it.cardPan.equals(cardpan) }.let { list ->
            if (list.isEmpty()) {
                bankAccount = BankAccount(
                    0L,
                    cardpan,
                    banks.filter { it.smsAddress.equals(sms.sender) }.first().id,
                    CardType.NOTYPE,
                    0.0,
                    balance
                )
                bankAccounts.add(bankAccount!!)
                dbRepository.insertBankAccountEntity(bankAccountConverter(bankAccount!!))
                //TODO bankAccountId =
            } else {
                bankAccountId = list.first().id
                list.first().balance= balance
                dbRepository.insertBankAccountEntity(bankAccountConverter(list.first()))
            }
        }

        pattern = Pattern.compile("[A-Z.]+")
        matcher = pattern.matcher(sms.body)
        var sellerName = ""
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: cardSpan = ${matcher.group()}")
            sellerName = matcher.group()
        } else {
            return null
        }
        var seller:Seller? = null
        var sellerId = 0L
        sellers.filter { it.name.equals(sellerName) }.let { sellerList ->
            if (sellerList.isEmpty()){
                val bdId = budgetGroups.filter { it.name.equals("НЕ ОПРЕДЕЛЕНО") }.first().id
                seller = Seller(0L,sellerName, bdId )
                sellers.add(seller!!)
                dbRepository.insertSellerEntity(sellerConverter(seller!!))
            } else{
                sellerId= sellerList.first().id
            }
        }


        for (card in bankAccounts) {
            if (sms.body.contains(card.cardPan, true)) {
                sms.bankAccountFound = true
                for (seller in sellers) {
                    if (sms.body.contains(seller.name)) {
                        sms.sellerFound = true
                        val amount = 0.0



                        budgetEntry = BudgetEntry(
                            id = sms.date,
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

    fun banksUpdate(newBanks: List<Bank>) {
        banks = newBanks as MutableList<Bank>
    }

    fun updateSellers(newSellers: List<Seller>) {
        sellers = newSellers as MutableList<Seller>
    }

    fun updateBudgetGroup(newBudgetGroups: List<BudgetGroup>){
        budgetGroups = newBudgetGroups as MutableList<BudgetGroup>
    }

    fun updateBankAccounts(newCards: List<BankAccount>) {
        bankAccounts = newCards as MutableList<BankAccount>
    }


}