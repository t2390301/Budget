package com.example.budget.repository

import com.example.budget.model.utils.DatabaseHelper
import com.example.budget.model.database.entity.toSmsData
import com.example.budget.model.database.entity.toSmsDataEntity
import com.example.budget.model.domain.SmsData

class SmsDataRepository (databaseHelper: DatabaseHelper) {

    private val smsDataDao = databaseHelper.getSmsDataDao()

    suspend fun getSmsData(): List<SmsData> {
        return smsDataDao.getAll().map {
            it.toSmsData()
        }
    }

    suspend fun insertSmsData(smsData: SmsData) =
        smsDataDao.insert(smsData.toSmsDataEntity())
}