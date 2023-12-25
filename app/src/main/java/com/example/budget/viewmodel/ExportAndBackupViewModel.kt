package com.example.budget.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.domain.CombainBudgetEntry
import com.example.budget.repository.DBRepository
import com.example.budget.repository.ExcelRepository
import kotlinx.coroutines.launch

class ExportAndBackupViewModel: ViewModel() {

    companion object {
        const val TAG = "ExportAndBackupViewModel"
    }
        val application = App.app
        val excelRepository = ExcelRepository(application)
        val dbRepository = DBRepository(application.getDatabaseHelper())


    var _budgetEntitiesListForExcel =
        MutableLiveData<AppState<MutableList<CombainBudgetEntry>>>()

    fun getBudgetEntitiesForExcel(): LiveData<AppState<MutableList<CombainBudgetEntry>>> {
        return _budgetEntitiesListForExcel
    }

    fun updateBudgetEntitiesForExcel(){
        viewModelScope.launch {
            _budgetEntitiesListForExcel.value = AppState.Loading(true)
            val combainList = dbRepository.getCombainBudgetEntities()
            if (combainList.isEmpty()) {
                Log.i(TAG, "getBudgetEntitiesForExcel: CombainListIsEmpty")
            } else{
                _budgetEntitiesListForExcel.value =AppState.Success(combainList.toMutableList())
            }

        }
    }

    fun convertBudgetEntriesToExcel(listCombainBudgetEntry: List<CombainBudgetEntry>){
        viewModelScope.launch {
            excelRepository.convertToExcel(dataList = listCombainBudgetEntry)
        }
    }

    fun getUri(): Uri?=
        excelRepository.getFileUri()



}