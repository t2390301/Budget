package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toBank
import com.example.budget.model.database.entity.toBankEntity
import com.example.budget.model.domain.Bank

class BankRepository(databaseHelper: DatabaseHelper) {

    private val bankDao = databaseHelper.getBanksDAO()

    suspend fun getBanks(): List<Bank> = bankDao.getAll().map {
        it.toBank()
    }

    suspend fun insertBank(bank: Bank) =
        bankDao.insert(bank.toBankEntity())
}



