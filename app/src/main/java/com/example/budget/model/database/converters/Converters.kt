package com.example.budget.model.database.converters

import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.database.entity.SmsDataEntity
import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.repository.DBRepository

class Converters(private val dbRepository: DBRepository) {
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
                bankAccount.cardType,
                bankAccount.cardLimit,
                bankAccount.balance
            )
        }
        return null
    }

    suspend fun bankAccountEntityConverter(bankAccountEntity: BankAccountEntity): BankAccount? {
        val smsAddress = dbRepository.getBankSMSAdress(bankAccountEntity.bankId)
        val banks = dbRepository.getBankEntities()
        var bankImageId: Int? = null
        if (banks.isNotEmpty() && banks.filter { it.smsAddress.equals(smsAddress) }.isNotEmpty() ) {
            bankImageId = banks.filter { it.smsAddress.equals(smsAddress) }.first().bankImage
        }
        if (smsAddress.length > 0) {
            return BankAccount(
                bankAccountEntity.id,
                bankAccountEntity.cardPan,
                smsAddress,
                bankAccountEntity.cardType,
                bankAccountEntity.cardLimit,
                bankAccountEntity.balance,
                bankImageId
            )
        } else {
            return null
        }
    }

    suspend fun sellerEntityConverter(sellerEntity: SellerEntity): Seller? {
        val budgetGroup = dbRepository.getBudgetGroupNameById(sellerEntity.id)
        return Seller(
                sellerEntity.name,
                budgetGroup.name
            )
    }

    suspend fun sellerConverter(seller: Seller): SellerEntity?  {
        val groupId = dbRepository.getBudgetGroupIdByBudgetGroupName(seller.budgetGroupName)
        if (groupId > 0) {
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
        val bankAccountId = dbRepository
            .getBankAccountIdBySMSAddressAndCardSpan(
            budgetEntry.bankSMSAdress,budgetEntry.cardSPan
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
            bankEntity.bankImage
        )

    fun bankConverter(bank: Bank): BankEntity =
        BankEntity(
            bank.id,
            bank.name,
            bank.smsAddress, "", "", "", "", "", "", null
        )

    fun smsDataConverter(smsData: SmsData): SmsDataEntity =
        SmsDataEntity(
            smsData.date,
            smsData.sender,
            smsData.body,
            smsData.isCashed
        )

    suspend fun smsDataEntityConverter(
        smsDataEntity: SmsDataEntity,
        banks: List<BankEntity>
    ): SmsData {
        return SmsData(
            smsDataEntity.date,
            smsDataEntity.sender,
            smsDataEntity.body,
            smsDataEntity.isCashed,

            bankImage = banks.filter { it.smsAddress.equals(smsDataEntity.sender) }
                .first().bankImage
        )

    }
}