package com.example.budget.model.constants

import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetGroupEntity


const val LAST_SAVED_SMS_Date = "last_saved_sms_date"

val BANKSENTITY = listOf<BankEntity>(
    BankEntity(
        1L, "Альфа Банк", "AlfaBank",
        "Pokupka",
        "Postupleniye",
        "^\\*\\*[\\d]{4}",
        "\\/[A-Za-z\\s]+\\/(.[^:]+)[\\s][\\d]{2}[\\.]",
        "Summa: ([\\d\\s\\,]+) RUR",
        "Ostatok: ([\\d\\s\\,]+) RUR"
    ),
    BankEntity(
        2L, "Тинькофф", "Tinkoff",
        "Покупка",
        "Поступление",     //"Проверить"
        "карта (\\*\\d{4}).",
        "RUB. (.+?). Доступно",
        "\\d. ([\\d\\.]+) RUB.",
        "Доступно ([\\d\\.]+) RUB."
    ),
    BankEntity( 3L, "UniCredit", "UniCredit",
        "Pokupka",
        "uvelichen",
        "Karta (\\d{4})",
        "RUB (.+)$",
        "[^:] ([\\d\\.\\s]+) RUB",
        "Dostupno: ([\\d\\.\\s]+) RUB"
        ),
    BankEntity(
        4L, "Test Bank", "+71111111111",
        "Покупка",         //Такие как у tinkoff
        "Поступление",
        "карта (\\*\\d{4}).",
        "RUB. (.+?). Доступно",
        "\\d. ([\\d\\.]+) RUB.",
        "Доступно ([\\d\\.]+) RUB."
    ),
    BankEntity(
        5L, "МТС Банк",
        "MTS-Bank",
        "Oplata",
        "Postuplenie",       //Не уверен
        "[\\*][\\d]{4}\\Z",
        "RUB (.+) Ostatok",
        "([\\d\\s\\.]+) RUB[^;]",  //убрать пробулы перед toDouble
        "Ostatok: ([\\d\\s\\.]+) RUB"        //убрать пробелы перед toDouble
    )


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

