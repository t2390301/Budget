package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.constants.BudgetGroupEnum

@Entity(
    tableName = "budget_group_table"
)
data class BudgetGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: BudgetGroupEnum,
    val description: String,
    val iconResId: Long
)
