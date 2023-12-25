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

    @Query("SELECT * FROM bank_table WHERE smsAddress =:bankAddress")
    suspend fun getBankListWithAddress(bankAddress: String): List<BankEntity>

    @Query("SELECT * FROM bank_table WHERE id =:id")
    suspend fun getBankListWithID(id: Long): List<BankEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BankEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<BankEntity>)

    @Update
    suspend fun update(entity: BankEntity)

    @Delete
    suspend fun delete(entity: BankEntity)
}