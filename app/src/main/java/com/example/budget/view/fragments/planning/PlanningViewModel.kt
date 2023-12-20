package com.example.budget.view.fragments.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.dao.PlanningNoteDao
import com.example.budget.model.database.entity.PlanningNoteEntity
import com.example.budget.model.domain.OperationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class PlanningViewModel : ViewModel() {

    private var dao: PlanningNoteDao? = null

    private val mutableList = mutableListOf<PlanningNoteEntity>()
    val notes get() = mutableList

    var operation: OperationType? = null
    var budgetGroup: BudgetGroupEnum? = null

    var onUpdateListEvent: (List<PlanningNoteEntity>) -> Unit = { }

    fun init(dao: PlanningNoteDao) {
        this.dao = dao
    }

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao?.getAll() ?: return@launch
            notes.addAll(list)
            onUpdateListEvent.invoke(notes)
        }
    }

    fun save(
        date: Date,
        description: String,
        value: Long
    ) {
        val operationType = operation ?: return
        val budgetGroup = budgetGroup ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val note = PlanningNoteEntity(
                id = 0,
                date = date,
                operationType = operationType,
                budgetGroup = budgetGroup,
                description = description,
                value = value
            )
            val localId = dao?.insert(note)
            if (localId != null) {
                mutableList.add(note.copy(id = localId))
                onUpdateListEvent.invoke(notes)
            }
        }

        operation = null
        this.budgetGroup = null
    }
}