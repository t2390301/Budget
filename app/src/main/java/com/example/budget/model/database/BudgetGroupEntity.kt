package com.example.budget.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_group_table")
data class BudgetGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val rules: List<String>
)
