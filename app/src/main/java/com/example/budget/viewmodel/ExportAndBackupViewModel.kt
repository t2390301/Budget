package com.example.budget.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.domain.CombainBudgetEntry
import com.example.budget.model.utils.AppLogger
import com.example.budget.repository.DBRepository
import com.example.budget.repository.ExcelRepository
import kotlinx.coroutines.launch

class ExportAndBackupViewModel(private val dbRepository: DBRepository) : ViewModel() {

    val application = App.app
    val excelRepository = ExcelRepository(application)

    var _budgetEntitiesListForExcel =
        MutableLiveData<AppState<MutableList<CombainBudgetEntry>>>()

    fun getBudgetEntitiesForExcel(): LiveData<AppState<MutableList<CombainBudgetEntry>>> =
        _budgetEntitiesListForExcel

    fun updateBudgetEntitiesForExcel() {
        viewModelScope.launch {
            _budgetEntitiesListForExcel.value = AppState.Loading(true)
            val combainList = dbRepository.getCombainBudgetEntitis()
            if (combainList.isEmpty()) {
                AppLogger.i("getBudgetEntitiesForExcel: CombainListIsEmpty")
            } else {
                _budgetEntitiesListForExcel.value = AppState.Success(combainList.toMutableList())
                AppLogger.i("getBudgetEntitiesForExcel: $combainList")
            }

        }
    }

    fun convertBudgetEntriesToExcel(listCombainBudgetEntry: List<CombainBudgetEntry>) {
        viewModelScope.launch {
            excelRepository.convertToExcel(dataList = listCombainBudgetEntry)
        }
    }

    fun getUri(): Uri? =
        excelRepository.getFileUri()


}