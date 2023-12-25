package com.example.budget.model.domain


data class SmsData(
    var date: Long,
    var sender: String,
    var body: String,
    var isCashed: Boolean,
    val bankImage: Int?

)