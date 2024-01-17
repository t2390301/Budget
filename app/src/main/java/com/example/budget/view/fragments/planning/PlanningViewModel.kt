package com.example.budget.view.fragments.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.PlanningNote
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date

class PlanningViewModel(private val dbRepository: DBRepository) : ViewModel() {

    private val planningNotes = mutableListOf<PlanningNote>()
    val notes get() = planningNotes
    var operation: OperationType? = null
    var budgetGroup: BudgetGroupEnum? = null
    var onUpdateListEvent: (List<PlanningNote>) -> Unit = { }

    init {
        Timber.i("init : PlanningViewModel")
        viewModelScope.launch(Dispatchers.IO) {
            planningNotes.addAll(dbRepository.getPlanningNotes())
            Timber.i("init : planningNoteRepository")
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
            dbRepository.insertPlanningNote(note)
            onUpdateListEvent.invoke(notes)
        }
        operation = null
        this.budgetGroup = null
    }
}