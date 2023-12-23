package com.example.budget.repository

import com.example.budget.model.utils.DatabaseHelper

class SellerRepository(databaseHelper: DatabaseHelper) {

    private val sellerDao = databaseHelper.getSellerDao()

/*    suspend fun getSellers(): List<Seller> {
        return sellerDao.getAll().map {
            it.toSeller()
        }
    }


    suspend fun insertSeller(sellerEntity: Seller) =
        sellerDao.insert(sellerEntity.toSellerEntity())*/
}
