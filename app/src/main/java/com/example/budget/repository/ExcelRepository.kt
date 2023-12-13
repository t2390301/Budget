package com.example.budget.repository

import android.content.Context
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.utils.ConvertToExcel

class ExcelRepository(appLicationContext: Context) {
    companion object {
        const val TAG = "ExcelRepository"
    }

    private val convertToExcel = ConvertToExcel(appLicationContext)

    suspend fun convertToExcel(
        dirName: String,
        fileName: String,
        dataList: List<BudgetEntry>, //= BUDGET_ENTRY,
        sheetName: String,
        headerColNames: List<String>
    ) {
        convertToExcel.convertBudgetEntryToExcel(
            dirName,
            fileName,
            dataList,
            sheetName,
            headerColNames
        )
    }
}