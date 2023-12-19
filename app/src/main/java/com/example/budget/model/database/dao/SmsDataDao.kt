package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.entity.SmsDataEntity

@Dao
interface SmsDataDao {

    @Query("SELECT * FROM sms_data_table")
    suspend fun getAll(): List<SmsDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SmsDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<SmsDataEntity>)

    @Update
    suspend fun update(entity: SmsDataEntity)

    @Delete
    suspend fun delete(entity: SmsDataEntity)
}