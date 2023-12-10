package com.example.budget.repository

import com.example.budget.model.database.AppDatabase
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.SellerEntity

class DBRepository(private  val db: AppDatabase) {
    companion object{
        const val TAG = "DBRepository"
    }

    private val bankAccountDao = db.bankAccountDao()
    private val bankDao = db.bankDao()
    private val budgetEntryDao = db.budgetEntryEntityDao()
    private val budgetGroupDao = db.budgetGroupEntityDao()
    private val sellerDao = db.sellerDao()
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

}