package com.example.budget.model.database.converters

import androidx.room.TypeConverter
import com.example.budget.model.constants.BudgetGroupEnum


class BudgetGroupConverter {
    @TypeConverter
    fun fromBudgetGroup(value: BudgetGroupEnum): Int {

        return value.id
    }

    @TypeConverter
    fun toBudgetGroup(strValue: String): BudgetGroupEnum {

        return enumValueOf<BudgetGroupEnum>(strValue)
    }
}