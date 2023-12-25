package com.example.budget.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.domain.OperationType
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val dbRepository: DBRepository) : ViewModel() {


    private val mutableList = mutableListOf<BudgetEntryEntity>()

    val budgetEntry get() = mutableList

    var operation: OperationType? = null
    var budgetGroup: BudgetGroupEnum? = null

    var onUpdateListEvent: (List<BudgetEntryEntity>) -> Unit = {}


    fun loadBudgetEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dbRepository.getBudgetEntities()
            budgetEntry.addAll(list)
            onUpdateListEvent.invoke(budgetEntry)
        }
    }

}