package com.example.budget.repository

import com.example.budget.model.utils.DatabaseHelper
import com.example.budget.model.database.entity.toPlanningNote
import com.example.budget.model.database.entity.toPlanningNoteEntity
import com.example.budget.model.domain.PlanningNote

class PlanningNoteRepository(databaseHelper: DatabaseHelper) {

    private val planningNoteDao = databaseHelper.getPlanningNoteDao()

    suspend fun getPlanningNote(): List<PlanningNote> {
        return planningNoteDao.getAll().map {
            it.toPlanningNote()
        }
    }

    suspend fun insertSeller(planningNote: PlanningNote) =
        planningNoteDao.insert(planningNote.toPlanningNoteEntity())
}