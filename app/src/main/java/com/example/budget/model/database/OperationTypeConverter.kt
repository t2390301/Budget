package com.example.budget.model.database

import androidx.room.TypeConverter
import com.example.budget.model.domain.OperationType

class OperationTypeConverter {
    @TypeConverter
    fun fromOperationType(value: OperationType): Int {
        return value.id
    }

    @TypeConverter
    fun toOperationType(intValue: Int): OperationType {
        return OperationType.entries.find { it.id == intValue } ?: OperationType.EXPENSE
    }
}