package com.example.budget.model.domain

data class BudgetGroupWithAmount(
    val id: Long,
    var name: String,
    var description: String,
    var iconResId : Int?,
    var totalAmount: Double?
) {

}
