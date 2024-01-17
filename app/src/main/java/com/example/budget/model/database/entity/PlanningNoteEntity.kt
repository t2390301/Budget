package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.PlanningNote
import java.util.Date

@Entity(
    tableName = "planning_note"
)
data class PlanningNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val operationType: OperationType,
    val budgetGroup: BudgetGroupEnum,
    val description: String,
    val value: Long,
)

fun PlanningNoteEntity.toPlanningNote(): PlanningNote {
    return PlanningNote(
        id, date, operationType, budgetGroup, description, value
    )
}

fun PlanningNote.toPlanningNoteEntity(): PlanningNoteEntity {
    return PlanningNoteEntity(
        id, date, operationType, budgetGroup, description, value
    )
}