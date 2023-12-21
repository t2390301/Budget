package com.example.budget.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.dao.BudgetEntryDao
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.domain.OperationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {
    private var dao: BudgetEntryDao? = null

    private val mutableList = mutableListOf<BudgetEntryEntity>()

    val budgetEntry get() = mutableList

    var operation: OperationType? = null
    var budgetGroup: BudgetGroupEnum? = null

    var onUpdateListEvent: (List<BudgetEntryEntity>) -> Unit = {}

    fun init(dao: BudgetEntryDao) {
        this.dao = dao
    }

    fun loadBudgetEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao?.getAll() ?: return@launch
            budgetEntry.addAll(list)
            onUpdateListEvent.invoke(budgetEntry)
        }
    }

}