package com.example.budget.model.database.converters

import android.util.Log
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.database.entity.SmsDataEntity
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.repository.DBRepository

class Converters(val dbRepository: DBRepository) {
    companion object {
        const val TAG = "Converters"
    }

    suspend fun bankAccountConverter(bankAccount: BankAccount): BankAccountEntity? {
        val bankId: Long = dbRepository.getBankEntityWithName(bankAccount.bankSMSAddress)
        if (bankId > 0) {
            return BankAccountEntity(
                id = bankAccount.id,
                bankAccount.cardPan,
                bankId,
                bankAccount.bankSMSAddress,
                bankAccount.cardType,
                bankAccount.cardLimit,
                bankAccount.balance
            )
        }
        return null
    }

    suspend fun bankAccountEntityConverter(bankAccountEntity: BankAccountEntity): BankAccount? {
        val smsAddress = dbRepository.getBankSMSAdress(bankAccountEntity.bankId)
        Log.i(TAG, "bankAccountEntityConverter: ${bankAccountEntity.bankId} $smsAddress ")
        if (smsAddress.length > 0) {
            return BankAccount(
                bankAccountEntity.id,
                bankAccountEntity.cardPan,
                smsAddress,
                bankAccountEntity.cardType,
                bankAccountEntity.cardLimit,
                bankAccountEntity.balance
            )
        } else {
            return null
        }
    }

    suspend fun sellerEntityConverter(sellerEntity: SellerEntity): Seller? {
        val budgetGroup = dbRepository.getBudgetGroupNameById(sellerEntity.id)
        if (budgetGroup in BudgetGroupEnum.entries) {
            return Seller(
                sellerEntity.name,
                budgetGroup
            )
        } else {
            return null
        }
    }

    suspend fun sellerConverter(seller: Seller): SellerEntity? {
        Log.i(TAG, "sellerConverter: seller.bg = ${seller.budgetGroupName}")
        val groupId = dbRepository.getBudgetGroupIdByBudgetGroupName(seller.budgetGroupName)
        Log.i(TAG, "sellerConverter: $groupId")
        if (groupId >= 0) {
            val seller = SellerEntity(
                0L,
                seller.name,
                groupId
            )
            Log.i(TAG, "sellerConverter: $seller")
            return seller
        } else {
            return null
        }
    }

    suspend fun budgetEntryConverter(budgetEntry: BudgetEntry): BudgetEntryEntity? {
        val bankAccountId = dbRepository.getBankAccountIdBySMSAddressAndCardSpan(
            budgetEntry.bankSMSAdress,
            budgetEntry.cardSPan
        )
        val sellerId = dbRepository.getSellerIdBySellerName(budgetEntry.sellerName)
        if (bankAccountId >= 0) {
            return BudgetEntryEntity(
                id = 0L,
                date = budgetEntry.date,
                operationType = budgetEntry.operationType,
                bankAccountId = bankAccountId,
                note = budgetEntry.note,
                operationAmount = budgetEntry.operationAmount,
                sellerId = sellerId
            )
        } else {
            return null
        }
    }
    /*
        fun bankEntityConverter(bankEntity: BankEntity): Bank =
            Bank(
                bankEntity.id,
                bankEntity.name,
                bankEntity.smsAddress,
                bankEntity.operationTypeEXPENSERegex,
                bankEntity.operationTypeINCOMERegex,
                bankEntity.cardPanRegex,
                bankEntity.sellerNameRegex,
                bankEntity.operationAmountRegex,
                bankEntity.balanceRegex,
            )

        fun bankConverter(bank: Bank): BankEntity =
            BankEntity(
                bank.id,
                bank.name,
                bank.smsAddress, "", "", "", "","",""
            )*/

    fun smsDataConverter(smsData: SmsData): SmsDataEntity =
        SmsDataEntity(
            smsData.date,
            smsData.sender,
            smsData.body,
            smsData.isCashed
        )
}