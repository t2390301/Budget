package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.entity.BankAccountEntity

@Dao
interface BankAccountDao {

    @Query("SELECT * FROM bank_account_table")
    suspend fun getAll(): List<BankAccountEntity>

    @Query("SELECT * FROM bank_account_table WHERE cardPan =:cardSpan AND bankId = :bankId")
    suspend fun getBankAccountIdBySMSAddressAndCardSpan(bankId: Long, cardSpan: String): List<BankAccountEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BankAccountEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<BankAccountEntity>)

    @Update
    suspend fun update(entity: BankAccountEntity)

    @Delete
    suspend fun delete(entity: BankAccountEntity)
}