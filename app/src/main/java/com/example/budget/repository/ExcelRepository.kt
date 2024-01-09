package com.example.budget.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.example.budget.model.domain.CombainBudgetEntry
import com.example.budget.model.utils.ConvertToExcel
import java.io.File

class ExcelRepository(val applicationContext: Context) {
    companion object {
        const val TAG = "ExcelRepository"
        const val APP_DIRECTORY_NAME = "reports"
        const val FILE_NAME = "EntryReport.xls"       //Output file .xls
        const val SHEET_NAME = "Report"   // Excel sheet name
        val HEADER_LIST = listOf(
            "Date",
            "Bank",
            "CardPan",
            "Operation_amount",
            "Seller",
            "Budget group",
        )
    }

    private val convertToExcel = ConvertToExcel(applicationContext)

    fun convertToExcel(
        dataList: List<CombainBudgetEntry>, //= BUDGET_ENTRY,
    ) {
        convertToExcel.convertBudgetEntryToExcel(
            dirName = APP_DIRECTORY_NAME,
            fileName = FILE_NAME,
            dataList = dataList,
            sheetName = SHEET_NAME,
            headerColNames = HEADER_LIST,
        )
    }

    fun getFileUri(fromDirectory: String = "reports",
                   fileName: String ="EntryReport.xls"): Uri? {
        val fileUri: Uri?
        val reportPath = File(applicationContext.filesDir, fromDirectory)
        Log.i(TAG, "onCreate: path exist ${reportPath.exists()}")
        if (!reportPath.exists()) {
            Log.i(TAG, "shareFile: Report path not found")
            return null
        }
        val requestFile = File(reportPath, fileName)
        if (!requestFile.exists()) {
            Log.i(TAG, "shareFile: File report not found")
            return null
        } else {
            Log.i(TAG, "FileName - ${requestFile} exist")
            Log.i(TAG, "File length = ${requestFile.length()}")
            fileUri =
                try {
                    FileProvider.getUriForFile(
                        applicationContext,
                        "com.geekbrain.budget.fileprovider",
                        requestFile
                    )
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    Log.i(
                        TAG,
                        "The selected file can't be shared: $requestFile - ${e.printStackTrace()}"
                    )
                    null
                }
            Log.i(TAG, "FileUri: ${fileUri.toString()}")
        }
        return fileUri

    }
}