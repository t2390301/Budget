package com.example.budget.model.constants

import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetGroupEntity


const val LAST_SAVED_SMS_Date = "last_saved_sms_date"

val BANKSENTITY = listOf<BankEntity>(
    BankEntity(1L, "Альфа Банк", "AlfaBank"),
    BankEntity(2L, "Тинькофф", "Tinkoff"),
    BankEntity( 3L, "UniCredit", "UniCredit"),  //cardpan без звездочки
    BankEntity(4L, "Test Bank", "+71111111111")

)

val BUDGETGROUPS = listOf<BudgetGroupEntity>(
    BudgetGroupEntity(1,BudgetGroupEnum.НЕ_ОПРЕДЕЛЕНО, "", 0L),
    BudgetGroupEntity(2,BudgetGroupEnum.ПРОДУКТЫ, "", 0L),
    BudgetGroupEntity(3,BudgetGroupEnum.ТРАНСПОРТ, "", 0L),
    BudgetGroupEntity(4,BudgetGroupEnum.РАЗВЛЕЧЕНИЯ, "", 0L),
    BudgetGroupEntity(5,BudgetGroupEnum.УСЛУГИ, "", 0L),
    BudgetGroupEntity(6,BudgetGroupEnum.ДОМАШНЕЕ_ХОЗЯЙСТВО, "", 0L),
    BudgetGroupEntity(7,BudgetGroupEnum.ЕДА_ВНЕ_ДОМА, "", 0L),

)

enum class BudgetGroupEnum(val id : Int){
    НЕ_ОПРЕДЕЛЕНО(1),
    ПРОДУКТЫ(2),
    ТРАНСПОРТ(3),
    РАЗВЛЕЧЕНИЯ(4),
    УСЛУГИ(5),
    ДОМАШНЕЕ_ХОЗЯЙСТВО(6),
    ЕДА_ВНЕ_ДОМА(7),
}

