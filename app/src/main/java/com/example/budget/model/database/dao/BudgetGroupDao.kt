package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.entity.BudgetGroupEntity

@Dao
interface BudgetGroupDao {

    @Query("SELECT * FROM budget_group_table")
    suspend fun getAll(): List<BudgetGroupEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BudgetGroupEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<BudgetGroupEntity>)

    @Update
    suspend fun update(entity: BudgetGroupEntity)

    @Delete
    suspend fun delete(entity: BudgetGroupEntity)
}