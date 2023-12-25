package com.example.budget.model.utils

import android.content.Context
import android.util.Log
import com.example.budget.model.domain.CombainBudgetEntry
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

class ConvertToExcel(private val context: Context) {
    companion object {
        const val TAG = "ConvertToExcel"
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

    fun convertBudgetEntryToExcel(
        dirName: String = APP_DIRECTORY_NAME,
        fileName: String = FILE_NAME,
        dataList: List<CombainBudgetEntry>, //= BUDGET_ENTRY,
        sheetName: String = SHEET_NAME,
        headerColNames: List<String> = HEADER_LIST,
    ) {
        //Get App Director, APP_DIRECTORY_NAME is a string
        val appDirectory = File(context.filesDir, dirName)  // context.getExternalFilesDir(dirName)
        Log.i(TAG, "convertBudgetEntryToExcel: directory exist ${appDirectory.exists()}")
        if (appDirectory != null && !appDirectory.exists()) {
            if (appDirectory.mkdir())
                Log.i(TAG, "convertBudgetEntryToExcel: directory $appDirectory made")
        }

        //Create excel file with extension .xlsx
        val excelFile = File(appDirectory, fileName)

        //Write workbook to file using FileOutputStream
        try {
            val fileOutStream = FileOutputStream(excelFile)
            val workbook = createWorkbook(sheetName, headerColNames, dataList)
            workbook.write(fileOutStream)
            workbook.close()
            Log.i(
                TAG,
                "convertBudgetEntryToExcel: File ${excelFile.name} in directory $appDirectory done "
            )
            fileOutStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createWorkbook(
        sheetName: String = SHEET_NAME,
        headerColNames: List<String> = HEADER_LIST,
        dataList: List<CombainBudgetEntry>  // = /BUDGET_ENTRY
    ): Workbook {
        // Creating excel workbook
        val workbook = HSSFWorkbook()

        //Creating first sheet inside workbook
        val sheet = workbook.createSheet(sheetName)

        //Creating sheet header row
        createSheetHeader(sheet, headerColNames)

        //Adding data to the sheet
        addData(1, sheet, dataList)

        return workbook
    }

    private fun addData(first_row: Int, sheet: Sheet, dataList: List<CombainBudgetEntry>) {
        var rowNum = first_row
        for (entry in dataList) {
            val row = sheet.createRow(rowNum)
            createCell(row, 0, Date(entry.date).toString())
            createCell(row, 1, entry.bankName)

            createCell(row, 2, entry.cardPan)
            createCell(row, 3, entry.operationAmount.toString())
            createCell(row, 4, entry.sellerName)
            createCell(row, 5, entry.budgetGroupName)
            rowNum++
        }
    }

    private fun createCell(row: Row, columnIndex: Int, value: String?) {
        val cell = row.createCell(columnIndex)
        cell?.setCellValue(value)
    }

    private fun createSheetHeader(sheet: Sheet, colNames: List<String>) {
        //Create sheet first row
        val row = sheet.createRow(0)
        for ((index, value) in colNames.withIndex()) {
            val cell = row.createCell(index)
            cell?.setCellValue(value)
        }
    }

    private fun getHeaderStyle(workbook: HSSFWorkbook): CellStyle {
        //Cell style for header row
        val cellStyle = workbook.createCellStyle()

        //Apply cell color
        //val colorMap = (workbook as HSSFWorkbook)  .stylesSource.indexedColors
        var color =
            HSSFColor.HSSFColorPredefined.GREY_25_PERCENT //IndexedColors.GREY_25_PERCENT, colorMap).indexed
        cellStyle.fillForegroundColor = color.index
        cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

        //Apply font style on cell text
        val brownFont = workbook.createFont()
        color =
            HSSFColor.HSSFColorPredefined.BROWN    //IndexedColors.BROWN, IndexedColors.CORAL,colorMap ) HSSFColor(IndexedColors.BROWN, colorMap).indexed
        brownFont.color = color.index
        brownFont.bold = true
        cellStyle.setFont(brownFont)
        return cellStyle
    }
}
