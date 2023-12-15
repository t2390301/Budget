package com.example.budget.repository

import com.example.budget.DatabaseHelper
import com.example.budget.model.database.entity.toSeller
import com.example.budget.model.database.entity.toSellerEntity
import com.example.budget.model.domain.Seller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SellerRepository(databaseHelper: DatabaseHelper) {

    private val sellerDao = databaseHelper.getSellerDao()

    suspend fun getSellers(): List<Seller> {
        return withContext(Dispatchers.IO) {
            sellerDao.getAll().map {
                it.toSeller()
            }
        }
    }


    suspend fun insertSeller(sellerEntity: Seller) =
        sellerDao.insert(sellerEntity.toSellerEntity())
}