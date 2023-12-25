package com.example.budget.view.fragments.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.PlanningNote
import com.example.budget.repository.PlanningNoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class PlanningViewModel(private val planningNoteRepository: PlanningNoteRepository) : ViewModel() {

    private val planningNotes = mutableListOf<PlanningNote>()
    val notes get() = planningNotes
    var operation: OperationType? = null
    var budgetGroup: BudgetGroupEnum? = null
    var onUpdateListEvent: (List<PlanningNote>) -> Unit = { }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            planningNotes.addAll(planningNoteRepository.getPlanningNotes())
        }
    }

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = planningNotes
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
            val note = PlanningNote(
                date = date,
                operationType = operationType,
                budgetGroup = budgetGroup,
                description = description,
                value = value
            )
            planningNotes.add(note)
            planningNoteRepository.insertPlanningNote(note)
            onUpdateListEvent.invoke(notes)
        }
        operation = null
        this.budgetGroup = null
    }
}