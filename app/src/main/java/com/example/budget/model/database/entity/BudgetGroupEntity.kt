package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.domain.BudgetGroup

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

fun BudgetGroupEntity.toBudgetGroup(): BudgetGroup {
    return BudgetGroup(
        id, name, description, iconResId
    )
}

fun BudgetGroup.toBudgetGroupEntity(): BudgetGroupEntity {
    return BudgetGroupEntity(
        id, name, description, iconResId
    )
}