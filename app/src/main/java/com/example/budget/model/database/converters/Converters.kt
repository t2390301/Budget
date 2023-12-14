package com.example.budget.model.database.converters

import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller
import com.example.budget.repository.DBRepository

class Converters (val dbRepository: DBRepository) {
    suspend fun bankAccountConverter(bankAccount: BankAccount): BankAccountEntity? {
        val bankId: Long = dbRepository.getBankEntityWithName(bankAccount.bankSMSAddress)
        if (bankId >= 0) {
            return BankAccountEntity(
                id = bankAccount.id,
                bankAccount.cardPan,
                bankId,
                bankAccount.cardType,
                bankAccount.cardLimit,
                bankAccount.balance
            )
        }
        return null
    }
    suspend fun bankAccountEntityConverter(bankAccountEntity: BankAccountEntity): BankAccount? {
        val smsAddress = dbRepository.getBankSMSAdress(bankAccountEntity.id)
        if (smsAddress.length >0) {
            return BankAccount(
                bankAccountEntity.id,
                bankAccountEntity.cardPan,
                smsAddress,
                bankAccountEntity.cardType,
                bankAccountEntity.cardLimit,
                bankAccountEntity.balance
            )
        } else{
            return null
        }
    }

    suspend fun sellerEntityConverter(sellerEntity: SellerEntity): Seller? {
        val budgetGroup = dbRepository.getBudgetGroupNameById(sellerEntity.id)
        if(budgetGroup in BudgetGroupEnum.entries) {
            return Seller(
                sellerEntity.name,
                budgetGroup
            )
        } else{
            return null
        }
    }

    suspend fun sellerConverter(seller: Seller): SellerEntity? {
        val groupId = dbRepository.getBudgetGroupIdByBudgetGroupName(seller.budgetGroupName)
        if (groupId >=0) {
            return SellerEntity(
                0L,
                seller.name,
                groupId
            )
        } else{
            return null
        }
    }

    suspend fun budgetEntryConverter(budgetEntry: BudgetEntry): BudgetEntryEntity? {
        val bankAccountId = dbRepository.getBankAccountIdBySMSAddressAndCardSpan(budgetEntry.bankSMSAdress, budgetEntry.cardSPan)
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
        } else{
            return null
        }
    }

    suspend fun bankEntityConverter(bankEntity: BankEntity): Bank =
        Bank(
            bankEntity.id,
            bankEntity.name,
            bankEntity.smsAddress
        )

    suspend fun bankConverter(bank: Bank): BankEntity =
        BankEntity(
            bank.id,
            bank.name,
            bank.smsAddress
        )
}