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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SellerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<SellerEntity>)

    @Update
    suspend fun update(entity: SellerEntity)

    @Delete
    suspend fun delete(entity: SellerEntity)

    @Query("SELECT * FROM seller_table WHERE name =:sellerName")
    suspend fun getSellerIdBySellerName(sellerName: String): List<SellerEntity>

    @Query("DELETE FROM seller_table")
    suspend fun deleteAll()


    suspend fun updateAll(sellersEntityList: List<SellerEntity>){
        for (sellerEntity in sellersEntityList){
            updateByName(sellerEntity.name, sellerEntity.budgetGroupId)
        }
    }

    @Query("UPDATE seller_table SET budgetGroupId = 1 WHERE budgetGroupId =:deleteId")
    suspend fun prepareForDelete(deleteId: Long)

    @Query("UPDATE seller_table SET budgetGroupId = :budgeGtId  WHERE name =:sellerName")
    suspend fun updateByName(sellerName: String, budgeGtId : Long)
}