package com.example.budget.model.constants

import com.example.budget.R
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetGroupEntity



val DEFAULT_BANK_IMAGE = R.drawable.ic_yandexdisk_64

val BANKSENTITY = listOf<BankEntity>(
    BankEntity(
        1L,
        "Альфа Банк",
        "AlfaBank",
        "Pokupka",
        "Postupleniye",
        "(^\\*\\*[\\d]{4})",
        "\\/[A-Za-z\\-\\s]+\\/([^:].+)[\\s][\\d]{2}[\\.]",
        "Summa: ([\\d\\s\\,]+) RUR",
        "Ostatok: ([\\d\\s\\,]+) RUR",
        R.drawable.ic_alfabank_72
    ),
    BankEntity(
        2L,
        "Тинькофф",
        "Tinkoff",
        "Покупка",
        "Поступление",     //"Проверить"
        "карта (\\*\\d{4}).",
        "RUB. (.+?). Доступно",
        "\\d\\. ([\\d\\.]+) RUB.", //не работает
        "Доступно ([\\d\\.]+) RUB",
        R.drawable.ic_tinkoff_72
    ),
    BankEntity( 3L, "UniCredit", "UniCredit",
        "Pokupka",
        "uvelichen",
        "Karta (\\d{4})",
        "RUB (.+)$",
        "[^:] ([\\d\\.\\s]+) RUB",
        "Dostupno: ([\\d\\.\\s]+) RUB",
        R.drawable.ic_unicredit_72
        ),
    BankEntity(
        4L, "Test Bank", "+71111111111",
        "Покупка",         //Такие как у tinkoff
        "Поступление",
        "карта (\\*\\d{4}).",
        "RUB. (.+?). Доступно",
        "\\d\\. ([\\d\\.]+) RUB.",
        "Доступно ([\\d\\.]+) RUB",
        R.drawable.ic_sberbank_72
    ),
    BankEntity(
        5L, "МТС Банк",
        "MTS-Bank",
        "Oplata",
        "Postuplenie",       //Не уверен
        "([\\*][\\d]{4})\\s\\Z",
        "RUB (.+)\\sOstatok",
        "([\\d\\s\\,]+) RUB[^;]",  //убрать пробелы перед toDouble
        "Ostatok: ([\\d\\s\\,]+) RUB",        //убрать пробелы перед toDouble
        R.drawable.ic_mtsbank_72
    )


)

val BUDGETGROUPS = listOf<BudgetGroupEntity>(
    BudgetGroupEntity(1,"НЕ_ОПРЕДЕЛЕНО","", 0L),
    BudgetGroupEntity(2,"ПРОДУКТЫ", "", 0L),
    BudgetGroupEntity(3,"ТРАНСПОРТ", "", 0L),
    BudgetGroupEntity(4,"РАЗВЛЕЧЕНИЯ", "", 0L),
    BudgetGroupEntity(5,"УСЛУГИ", "", 0L),
    BudgetGroupEntity(6,"ДОМАШНЕЕ_ХОЗЯЙСТВО", "", 0L),
    BudgetGroupEntity(7,"ЕДА_ВНЕ_ДОМА", "", 0L),

    /*BudgetGroupEntity(1,BudgetGroupEnum.НЕ_ОПРЕДЕЛЕНО, "", 0L),
    BudgetGroupEntity(2,BudgetGroupEnum.ПРОДУКТЫ, "", 0L),
    BudgetGroupEntity(3,BudgetGroupEnum.ТРАНСПОРТ, "", 0L),
    BudgetGroupEntity(4,BudgetGroupEnum.РАЗВЛЕЧЕНИЯ, "", 0L),
    BudgetGroupEntity(5,BudgetGroupEnum.УСЛУГИ, "", 0L),
    BudgetGroupEntity(6,BudgetGroupEnum.ДОМАШНЕЕ_ХОЗЯЙСТВО, "", 0L),
    BudgetGroupEntity(7,BudgetGroupEnum.ЕДА_ВНЕ_ДОМА, "", 0L),*/

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

