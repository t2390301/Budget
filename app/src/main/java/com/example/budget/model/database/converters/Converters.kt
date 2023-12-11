package com.example.budget.model.database.converters

import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.domain.BankAccount

fun bankAccountConverter(bankAccount: BankAccount): BankAccountEntity =
    BankAccountEntity(
        bankAccount.id,
        bankAccount.cardPan,
        bankAccount.bankId,
        bankAccount.cardType,
        bankAccount.cardLimit,
        bankAccount.balance
    )
