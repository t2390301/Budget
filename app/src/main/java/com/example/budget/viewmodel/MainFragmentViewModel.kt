package com.example.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
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
    private val application = App.app
    private val dbRepository = DBRepository(application.getDatabaseHelper())

    var budgetEntityTableList = getAllBudgetEntry()


    private fun getAllBudgetEntry(): LiveData<List<BudgetEntryTable>> {
        var budgetEntryTableList: List<BudgetEntryTable> = listOf()
        val liveData = MutableLiveData<List<BudgetEntryTable>>()
        Timber.tag(TAG).i("liveData $liveData")
        viewModelScope.launch {
            budgetEntryTableList = dbRepository.getBudgetEntriesTableDao()
            Timber.tag(TAG).i("budgetEntryTableList $budgetEntryTableList")
            if (budgetEntryTableList.isNotEmpty()) {
                liveData.value = budgetEntryTableList
                Timber.tag(TAG).i("budgetEntryTableList.isNotEmpty")
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