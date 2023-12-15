package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toSmsData
import com.example.budget.model.database.entity.toSmsDataEntity
import com.example.budget.model.domain.SmsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmsDataRepository(databaseHelper: DatabaseHelper) {

    private val smsDataDao = databaseHelper.getSmsDataDao()

    suspend fun getSmsData(): List<SmsData> {
        return withContext(Dispatchers.IO) {
            smsDataDao.getAll().map {
                it.toSmsData()
            }
        }
    }

    suspend fun insertSmsData(smsData: SmsData) =
        smsDataDao.insert(smsData.toSmsDataEntity())
}