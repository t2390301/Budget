package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.domain.BudgetGroup

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

fun BudgetGroupEntity.toBudgetGroup(): BudgetGroup {
    return BudgetGroup(
        name, description, iconResId
    )
}

fun BudgetGroup.toBudgetGroupEntity(): BudgetGroupEntity {
    return BudgetGroupEntity(
        id = 0, name, description, iconResId
    )
}