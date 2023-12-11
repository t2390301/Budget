package com.example.budget.model.constants

import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BudgetGroup


val BANKS = listOf<Bank>(
    Bank(1L, "Альфа Банк", "Alfa-Bank"),
    Bank(1L, "Тинькофф", "Tinkoff")
)

val BUDGETGROUPS = listOf<BudgetGroup>(
    BudgetGroup(1L, "ПРОДУКТЫ", "", 0L),
    BudgetGroup(1L, "ТРАНСПОРТ", "", 0L),
    BudgetGroup(3L, "РАЗВЛЕЧЕНИЯ", "", 0L),
    BudgetGroup(4L, "УСЛУГИ", "", 0L),
    BudgetGroup(4L, "ДОМАШНЕЕ ХОЗЯЙСТВО", "", 0L),
    BudgetGroup(4L, "ЕДА ВНЕ ДОМА", "", 0L),
)