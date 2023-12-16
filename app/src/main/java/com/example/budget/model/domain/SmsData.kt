package com.example.budget.model.domain


data class SmsData(
    val id: Long = 0,
    val date: Long,
    val sender: String,
    val body: String,
    var isCashed: Boolean,
    var bankAccountFound: Boolean,
    var sellerFound: Boolean,
)