package com.example.budget.model.domain


data class SmsData(
    val date: Long,
    val sender: String,
    val body: String,
    var isCashed: Boolean,

    )