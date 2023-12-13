package com.example.budget.model.database.converters

import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller

fun bankAccountConverter(bankAccount: BankAccount): BankAccountEntity =
    BankAccountEntity(
        //bankAccount.id,
        bankAccount.cardPan,
        bankAccount.bankId,
        bankAccount.cardType,
        bankAccount.cardLimit,
        bankAccount.balance
    )

fun bankAccountEntityConverter(bankAccountEntity: BankAccountEntity): BankAccount =
    BankAccount(
        bankAccountEntity.id,
        bankAccountEntity.cardPan,
        bankAccountEntity.bankId,
        bankAccountEntity.cardType,
        bankAccountEntity.cardLimit,
        bankAccountEntity.balance
    )

fun sellerEntityConverter(sellerEntity: SellerEntity): Seller =
    Seller(
        sellerEntity.id,
        sellerEntity.name,
        sellerEntity.budgetGroupId
    )

fun budgetEntryConverter(budgetEntry: BudgetEntry): BudgetEntryEntity =
    BudgetEntryEntity(
        date = budgetEntry.date,
        operationType = budgetEntry.operationType,
        bankAccountId = budgetEntry.bankAccountId,
        note = budgetEntry.note,
        operationAmount = budgetEntry.operationAmount,
        sellerId = budgetEntry.sellerId
    )