package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.BudgetGroupEntity

@Dao
interface BudgetGroupEntityDao {
    @Query("INSERT INTO budget_group_table (id,name, rules) VALUES(:id,:name,:rules)")
    suspend fun nativeInsert(
        id: Long,
        name: String,
        rules: List<String>
    )

    @Query("SELECT * FROM budget_group_table")
    suspend fun all(): List<BudgetGroupEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BudgetGroupEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<BudgetGroupEntity>)

    @Update
    suspend fun update(entity: BudgetGroupEntity)

    @Delete
    suspend fun delete(entity: BudgetGroupEntity)
}