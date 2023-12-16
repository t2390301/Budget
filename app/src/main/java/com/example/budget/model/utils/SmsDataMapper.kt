package com.example.budget.model.utils

import android.util.Log
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.converters.Converters
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
        val CURRENCYLIST = listOf("RUB","USD")
        const val SPACE = " "
    }

    lateinit var banks: MutableList<Bank>
    lateinit var sellers: MutableList<Seller>
    lateinit var bankAccounts: MutableList<BankAccount>
    lateinit var budgetGroups: MutableList<BudgetGroup>

    val converter = Converters(dbRepository)

    suspend fun convertSMSToBudgetEntry(sms: SmsData): BudgetEntry? {

        var budgetEntry: BudgetEntry?

        Log.i(TAG, "convertSMSToBudgetEntry: sms.adress = ${sms.sender}")
        Log.i(TAG, "convertSMSToBudgetEntry: sms.body = ${sms.body}")
        Log.i(TAG, "convertSMSToBudgetEntry: sms.body = ${sms.date}")
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

        val stringAfterCardPan = sms.body.substringAfter("$cardpan.")
        Log.i(TAG, "convertSMSToBudgetEntry: $stringAfterCardPan")
        pattern = Pattern.compile("\\d+\\.?\\d*")
        matcher = pattern.matcher(stringAfterCardPan)
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: matcher1 = ${matcher.group()}")
            amount = matcher.group().toDouble()
        } else{
            return null
        }

        var balance = 0.0
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: matcher2 = ${matcher.group()}")
            balance = matcher.group().toDouble()

            bankAccounts.filter { it.cardPan.equals(cardpan) }.let { list ->
                if (list.isEmpty()) {
                    val bankAccount = BankAccount(
                        0,
                        cardPan =  cardpan,
                        bankSMSAddress = sms.sender,
                        cardType= CardType.NOTYPE,
                        cardLimit = .0,
                        balance = balance
                    )?.let {
                        bankAccounts.add(it)
                        converter.bankAccountConverter(it)
                            ?.let { it1 -> dbRepository.insertBankAccountEntity(it1) }
                    }

                } else {
                    list.first().balance= balance
                    converter.bankAccountConverter(list.first())
                        ?.let { dbRepository.update(it) }//Update not insert
                }
            }
        } else{
            return null
        }

        var sellerName = ""
        pattern = Pattern.compile("[A-Za-z][A-Za-z0-9]{1}[-\\.A-Za-z0-9]{3,}")      //sellerName Regex Pattern
        matcher = pattern.matcher(sms.body)
        if (matcher.find()) {
            Log.i(TAG, "convertSMSToBudgetEntry: SellerName = ${matcher.group()}")
            sellerName = matcher.group()
            if (sellerName in CURRENCYLIST) {
                return null
            }
        } else {
            return null
        }


        var budgetGroup = BudgetGroupEnum.НЕ_ОПРЕДЕЛЕНО

        sellers.filter { it.name.equals(sellerName) }.let { sellerList ->
            if (sellerList.isEmpty()) {
               /* val budgetgroupentity = dbRepository.getBudgetGroupEntities()
                delay(1000)
                Log.i(TAG, "convertSMSToBudgetEntry: ${budgetgroupentity.size}")
                for (bd in budgetgroupentity){
                    Log.i(TAG, "convertSMSToBudgetEntry: ${bd.id}, ${bd.name}")
                }*/

                Seller(sellerName, BudgetGroupEnum.НЕ_ОПРЕДЕЛЕНО)?.let {
                    sellers.add(it)

                    converter.sellerConverter(it)
                        ?.let { it1 -> dbRepository.insertSellerEntity(it1) }
                }
            } else {
                budgetGroup = sellerList.first().budgetGroupName
            }
        }


        budgetEntry = BudgetEntry(
            date = Date(sms.date),
            operationType = OperationType.EXPENSE,
            cardSPan = cardpan,
            bankSMSAdress = sms.sender,
            note = "",
            operationAmount = amount,
            sellerName = sellerName,
            budgetGroup = budgetGroup,
        )

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

    fun updateBudgetGroup(newBudgetGroups: List<BudgetGroup>) {
        budgetGroups = newBudgetGroups as MutableList<BudgetGroup>
    }

    fun updateBankAccounts(newBankAcounts: List<BankAccount>) {
        bankAccounts = newBankAcounts as MutableList<BankAccount>
    }

    fun updateBanks(newList: List<Bank>) {
        banks = newList as MutableList<Bank>
    }


}