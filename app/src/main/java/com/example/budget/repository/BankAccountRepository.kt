package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toBankAccount
import com.example.budget.model.database.entity.toBankAccountEntity
import com.example.budget.model.domain.BankAccount

class BankAccountRepository(databaseHelper: DatabaseHelper) {

    private val bankAccountDao = databaseHelper.getBankAccountDao()

    suspend fun getBankAccounts(): List<BankAccount> = bankAccountDao.getAll().map {
        it.toBankAccount()
    }

    suspend fun insertBankAccount(bankAccount: BankAccount) =
        bankAccountDao.insert(bankAccount.toBankAccountEntity())
}