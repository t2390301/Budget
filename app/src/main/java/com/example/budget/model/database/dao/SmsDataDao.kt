package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.SmsDataEntity
import java.util.Date

@Dao
interface SmsDataDao {

    @Query("INSERT INTO sms_data_table (id,rawSms, date) VALUES(:id,:rawSms,:date)")
    suspend fun nativeInsert(
        id: Long,
        rawSms: String,
        date: Date
    )

    @Query("SELECT * FROM sms_data_table")
    suspend fun all(): List<SmsDataEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SmsDataEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<SmsDataEntity>)

    @Update
    suspend fun update(entity: SmsDataEntity)

    @Delete
    suspend fun delete(entity: SmsDataEntity)
}