package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budget.model.database.entity.PlanningNote

@Dao
interface PlanningNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PlanningNote): Long

    @Query("SELECT * FROM planning_note")
    suspend fun selectAll(): List<PlanningNote>

}