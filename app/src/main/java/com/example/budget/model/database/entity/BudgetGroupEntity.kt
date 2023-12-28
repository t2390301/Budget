package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "budget_group_table"
)
data class BudgetGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val iconResId: Long
)
