package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.domain.OperationType

@Dao
interface BudgetEntryDao {

    @Query("SELECT * FROM budget_entry_table")
    suspend fun getAll(): List<BudgetEntryEntity>

    @Query("SELECT * FROM budget_entry_table WHERE operationType = :operationType")
    suspend fun getByOperationType(operationType: OperationType): List<BudgetEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BudgetEntryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<BudgetEntryEntity>)

    @Update
    suspend fun update(entity: BudgetEntryEntity)

    @Delete
    suspend fun delete(entity: BudgetEntryEntity)
}