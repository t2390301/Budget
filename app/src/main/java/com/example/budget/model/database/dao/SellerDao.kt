package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.entity.SellerEntity

@Dao
interface SellerDao {

    @Query("SELECT * FROM seller_table")
    suspend fun getAll(): List<SellerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SellerEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<SellerEntity>)

    @Update
    suspend fun update(entity: SellerEntity)

    @Delete
    suspend fun delete(entity: SellerEntity)
}