package com.example.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntryTable
import com.example.budget.repository.DBRepository
import com.example.budget.view.fragments.main.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragmentViewModel : ViewModel() {
    companion object {
        const val TAG = "!!! MainFragmentViewModel"
    }

    private val dbRepository = DBRepository(App.app.getDatabaseHelper())
    val converters = Converters(dbRepository)

    var budgetEntityTableList = getAllBudgetEntry()

/*    fun retrieveItem(id: Long): MutableLiveData<BudgetEntryTable>? {

        val  liveData = MutableLiveData<BudgetEntryTable>()
        viewModelScope.launch {
            dbRepository.getBudgetEntriesTableDao() .getBankAccountEntityById(id)?.let {
                liveData.value = converters.budgetEntryConverter(it)
            }
        }
        return liveData
    }*/

    private fun getAllBudgetEntry(): LiveData<List<BudgetEntryTable>> {
        var budgetEntryTableList: List<BudgetEntryTable> = listOf()
        val liveData = MutableLiveData<List<BudgetEntryTable>>()
        viewModelScope.launch {
            budgetEntryTableList = dbRepository.getBudgetEntriesTableDao()
            if (budgetEntryTableList.isNotEmpty()) {
                liveData.value = budgetEntryTableList
            }
        }
        return liveData
    }

    /*
        private var dao: BudgetEntryDao? = null

        private val mutableList = mutableListOf<BudgetEntryTable>()

        val budgetEntry get() = mutableList

        var budgetGroup: BudgetGroupEnum? = null

        var onUpdateListEvent: (List<BudgetEntryTable>) -> Unit = {}

        fun init(dao: BudgetEntryDao) {
            this.dao = dao
        }

        fun loadBudgetEntry() {
            viewModelScope.launch(Dispatchers.IO) {
                val list = dao?.getAll() ?: return@launch
                budgetEntry.addAll(list)
                onUpdateListEvent.invoke(budgetEntry)
            }
        }*/

}