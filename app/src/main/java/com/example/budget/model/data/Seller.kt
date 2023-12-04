package com.example.budget.model.data

import com.example.budget.model.domain.TransactionSource

data class Seller(
    val name: String,
    val transactionSource: TransactionSource
)
