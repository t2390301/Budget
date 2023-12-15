package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toBankAccount
import com.example.budget.model.database.entity.toBankAccountEntity
import com.example.budget.model.domain.BankAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BankAccountRepository(databaseHelper: DatabaseHelper) {

    private val bankAccountDao = databaseHelper.getBankAccountDao()

    suspend fun getBankAccounts(): List<BankAccount> {
        return withContext(Dispatchers.IO) {
            bankAccountDao.getAll().map {
                it.toBankAccount()
            }
        }
    }

    suspend fun insertBankAccount(bankAccount: BankAccount) =
        bankAccountDao.insert(bankAccount.toBankAccountEntity())
}