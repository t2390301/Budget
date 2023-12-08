package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.entity.BankEntity

@Dao
interface BankDao {

    @Query("SELECT * FROM bank_table")
    suspend fun getAll(): List<BankEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BankEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<BankEntity>)

    @Update
    suspend fun update(entity: BankEntity)

    @Delete
    suspend fun delete(entity: BankEntity)
}