package com.example.budget.repository

import android.util.Log
import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.database.entity.SmsDataEntity
import com.example.budget.model.domain.BudgetGroupWithAmount
import com.example.budget.model.domain.BudgetEntryTable
import com.example.budget.model.domain.CombainBudgetEntry
import timber.log.Timber

class DBRepository(db: DatabaseHelper) {
    companion object {
        const val TAG = "DBRepository"
    }

    private val bankAccountDao = db.getBankAccountDao()
    private val budgetEntriesTableDao = db.getBudgetEntriesTableDao()
    private val bankDao = db.getBanksDAO()
    private val budgetEntryDao = db.getBudgetEntryEntityDao()
    private val budgetGroupDao = db.getBudgetGroupEntityDao()
    private val sellerDao = db.getSellerDao()
    private val smsDao = db.getSmsDataDao()
    private val combainBudgetEntryDao = db.getCombainBudgetEntriesDao()
    private val budgetGroupWithAmountDao = db.getBudgetGroupWithAmountDao()

    suspend fun getBankAccountEntities(): List<BankAccountEntity> {
        return bankAccountDao.getAll()
    }


    suspend fun getBankEntities(): List<BankEntity> = bankDao.getAll()


    suspend fun getBudgetEntities(): List<BudgetEntryEntity> = budgetEntryDao.getAll()
    suspend fun getBudgetGroupEntities(): List<BudgetGroupEntity> = budgetGroupDao.getAll()
    suspend fun getCombainBudgetEntities(): List<CombainBudgetEntry> = combainBudgetEntryDao.getAll()
    suspend fun getBudgetEntryList(): List<BudgetEntryEntity> = budgetEntryDao.getAll()
    suspend fun getSellerEntities(): List<SellerEntity> = sellerDao.getAll()
    suspend fun getBudgetEntriesTableDao(): List<BudgetEntryTable> {
        Timber.i("getBudgetEntriesTableDao: here")
        return budgetEntriesTableDao.getAll()
    }

    suspend fun insertBankAccountEntity(bankAccountEntity: BankAccountEntity) =
        bankAccountDao.insert(bankAccountEntity)

    suspend fun insertBankEntity(bankEntity: BankEntity) =
        bankDao.insert(bankEntity)

    suspend fun insertBudgetEntryEntity(budgetEntryEntity: BudgetEntryEntity) =
        budgetEntryDao.insert(budgetEntryEntity)

    suspend fun insertBudgetGroupEntity(budgetGroupEntity: BudgetGroupEntity) =
        budgetGroupDao.insert(budgetGroupEntity)

    suspend fun insertSellerEntity(sellerEntity: SellerEntity) =
        sellerDao.insert(sellerEntity)

    suspend fun getBankEntityWithName(bankSMSAddress: String): Long {
        var id = -1L
        bankDao.getBankListWithAddress(bankSMSAddress).first()?.let {
            id = it.id
        }
        return id
    }

    suspend fun getBankSMSAdress(id: Long): String {
        var smsAddress = ""
        val bankEntities = bankDao.getBankListWithID(id)
        if (!bankEntities.isEmpty()) {
            smsAddress = bankEntities.first().smsAddress
        }
        return smsAddress
    }

    suspend fun getBudgetGroupNameById(id: Long): BudgetGroupEntity {
        var budgetGroup = BudgetGroupEntity(1L,"НЕ ОПРЕДЕЛЕНО", "", 0)
        val bugetGroupEntityList = budgetGroupDao.getBudgetGroupNameById(id)
        if (!bugetGroupEntityList.isEmpty()) {
            budgetGroup= bugetGroupEntityList.first()
        }
        return budgetGroup
    }

    suspend fun getBudgetGroupIdByBudgetGroupName(budgetGroupName: String): Long {
        var id: Long

        val bdlist = budgetGroupDao.getBudgetGroupNameByGroupName(budgetGroupName)
        if (bdlist.isEmpty()) {
            id = 1
        } else {
            id = bdlist.first().id
        }
        return id
    }


    suspend fun getBankAccountIdBySMSAddressAndCardSpan(
        smsAddress: String,
        cardSpan: String
    ): Long {
        var id = -1L
        val banks = bankDao.getBankListWithAddress(smsAddress)
        if (!banks.isEmpty()) {
            val bankAccount =
                bankAccountDao.getBankAccountIdBySMSAddressAndCardSpan(banks.first().id, cardSpan)
            if (!bankAccount.isEmpty()) {
                id = bankAccount.first().id
            }
        }
        return id
    }


    suspend fun getSellerIdBySellerName(sellerName: String): Long {
        var id = -1L
        val sellerEntityList = sellerDao.getSellerIdBySellerName(sellerName)
        if (!sellerEntityList.isEmpty()) {
            id = sellerEntityList.first().id
        }
        return id
    }

    suspend fun updatebankAccountEntity (bankAccountEntity: BankAccountEntity) {
        bankAccountDao.update(bankAccountEntity)
    }

    suspend fun insertSMSDataEntityList(smsEntityList: List<SmsDataEntity>) {
        smsDao.insertAll(smsEntityList)
    }

    suspend fun getCombainBudgetEntitis(): List<CombainBudgetEntry> =
        combainBudgetEntryDao.getAll()

    suspend fun getSMSList(): List<SmsDataEntity> =
        smsDao.getAll()

    suspend fun getSMSCount(): Long =
        smsDao.getSMSCount()

    suspend fun insertSMSEntity(smsDataEntity: SmsDataEntity) =
        smsDao.insert(smsDataEntity)


    suspend fun getLastUnSafeSMSDate(): Long =
        smsDao.getLastUnsavedSMSDate()?:0



    suspend fun updateSMS(sms: SmsDataEntity) {
        smsDao.update(sms)
    }

    suspend fun getBankAccountEntityById(id: Long): BankAccountEntity? {
        val list = bankAccountDao.getBankAccountEntityById(id)
        if(list.isNotEmpty()){
            return  list.first()
        }
        return null
    }


    suspend fun updateSellersEntity(sellersEntityList: List<SellerEntity>) {

        sellerDao.updateAll(sellersEntityList)
    }

    suspend fun getBudgetGroupWithAmount(): List<BudgetGroupWithAmount> {
        return budgetGroupWithAmountDao.getAll()
    }

    suspend fun updateBudgetGroupEntity(budgetGroupEntity: BudgetGroupEntity) {
        budgetGroupDao.update(budgetGroupEntity)
    }


}
