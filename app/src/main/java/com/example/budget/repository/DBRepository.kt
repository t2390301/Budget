package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.SellerEntity

class DBRepository( db: DatabaseHelper) {
    companion object{
        const val TAG = "DBRepository"
    }

    private val bankAccountDao =  db.getBankAccountDao()
    private val bankDao = db.getBanksDAO()
    private val budgetEntryDao = db.getBudgetEntryEntityDao()
    private val budgetGroupDao = db.getBudgetGroupEntityDao()
    private val sellerDao = db.getSellerDao()
    suspend fun getBankAccountEntities() : List<BankAccountEntity> = bankAccountDao.getAll()
    suspend fun getBankEntities(): List<BankEntity> = bankDao.getAll()
    suspend fun getBudgetEntities(): List<BudgetEntryEntity> = budgetEntryDao.getAll()
    suspend fun getBudgetGroupEntities() :List<BudgetGroupEntity> = budgetGroupDao.getAll()
    suspend fun getSellerEntities(): List<SellerEntity> = sellerDao.getAll()
    suspend fun insertBankAccountEntity(bankAccountEntity: BankAccountEntity) =
        bankAccountDao.insert(bankAccountEntity)
    suspend fun insertBankEntity(bankEntity: BankEntity) =
        bankDao.insert(bankEntity)
    suspend fun insertBudgetEntryEntity(budgetEntryEntity: BudgetEntryEntity) =
        budgetEntryDao.insert(budgetEntryEntity)
    suspend fun insertBudgetGroupEntity(budgetGroupEntity: BudgetGroupEntity)=
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
        var smsAddress: String = ""
        bankDao.getBankListWithID(id).first()?.let {
            smsAddress = it.smsAddress
        }
        return smsAddress
    }

    suspend fun getBudgetGroupNameById(id: Long): BudgetGroupEnum{
        var budgetGroup = BudgetGroupEnum.НЕ_ОПРЕДЕЛЕНО
        budgetGroupDao.getBudgetGroupNameById(id).first()?.let{
            budgetGroup = it.name
        }
        return budgetGroup
    }

    suspend fun getBudgetGroupIdByBudgetGroupName(budgetGroup: BudgetGroupEnum): Long{
        var id = -1L
        budgetGroupDao.getBudgetGroupNameByGroupName(budgetGroup).first()?.let {
            id = it.id
        }
        return id
    }

    suspend fun getBankAccountIdBySMSAddressAndCardSpan(smsAddress: String, cardSpan: String): Long {
        var id = -1L
        bankDao.getBankListWithAddress(smsAddress).first()?.let {bankEntity ->
            bankAccountDao.getBankAccountIdBySMSAddressAndCardApan(bankEntity.id, cardSpan).first()?.let {
                return it.id
            }
        }
        return id
    }

    suspend fun getSellerIdBySellerName(sellerName: String): Long {
        var id = -1L
        sellerDao.getSellerIdBySellerName(sellerName).first()?.let {
            return it.id
        }
        return id
    }

    suspend fun update(bankAccountEntity: BankAccountEntity) {
        bankAccountDao.update(bankAccountEntity)

    }

}