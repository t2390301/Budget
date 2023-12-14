package com.example.budget.model.constants

import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BudgetGroup

const val LAST_SAVED_SMS_Date = "last_saved_sms_date"

val BANKS = listOf<Bank>(
    Bank(1L, "Альфа Банк", "Alfa-Bank"),
    Bank(1L, "Тинькофф", "Tinkoff")
)

val BUDGETGROUPS = listOf<BudgetGroup>(
    BudgetGroup("НЕ ОПРЕДЕЛЕНО", "", 0L),
    BudgetGroup("ПРОДУКТЫ", "", 0L),
    BudgetGroup( "ТРАНСПОРТ", "", 0L),
    BudgetGroup( "РАЗВЛЕЧЕНИЯ", "", 0L),
    BudgetGroup( "УСЛУГИ", "", 0L),
    BudgetGroup( "ДОМАШНЕЕ ХОЗЯЙСТВО", "", 0L),
    BudgetGroup( "ЕДА ВНЕ ДОМА", "", 0L),
)

enum class BudgetGroupEnum(val id : Int){
    НЕ_ОПРЕДЕЛЕНО(0),
    ПРОДУКТЫ(1),
    ТРАНСПОРТ(3),
    РАЗВЛЕЧЕНИЯ(4),
    УСЛУГИ(5),
    ДОМАШНЕЕ_ХОЗЯЙСТВО(6),
    ЕДА_ВНЕ_ДОМА(7),
}

