package com.example.budget.model.database.converters

import androidx.room.TypeConverter


class StringListConverter {

    private val separator = ","

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return if (list.isNullOrEmpty())
            ""
        else
            list.joinToString(separator = separator) {
                it
            }
    }

    @TypeConverter
    fun toStringList(string: String?): List<String> {
        return string?.split(separator) ?: listOf()
    }
}