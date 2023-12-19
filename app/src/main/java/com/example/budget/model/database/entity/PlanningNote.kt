package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.domain.OperationType
import java.util.Date

@Entity(tableName = "planning_note")
data class PlanningNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val operationType: OperationType,
    val budgetGroup: BudgetGroupEnum,
    val description: String,
    val value: Long,
)