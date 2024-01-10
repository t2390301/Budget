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

    @Query("SELECT * FROM budget_group_table WHERE id =:id")
    suspend fun getBudgetGroupNameById(id: Long): List<BudgetGroupEntity>

    @Query("SELECT * FROM budget_group_table WHERE name =:groupName")
    suspend fun getBudgetGroupNameByGroupName(groupName: String): List<BudgetGroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BudgetGroupEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<BudgetGroupEntity>)

    @Update
    suspend fun update(entity: BudgetGroupEntity)

    @Delete
    suspend fun delete(entity: BudgetGroupEntity)

    @Query("DELETE FROM budget_group_table WHERE id =:deletedId")
    suspend fun deleteById(deletedId: Long)
}