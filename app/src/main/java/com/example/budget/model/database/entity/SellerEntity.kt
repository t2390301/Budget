package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "seller_table", foreignKeys = [ForeignKey(
        entity = BudgetGroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["budgetGroupId"],
        onDelete = ForeignKey.SET_DEFAULT
    )]
)
data class SellerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val budgetGroupId: Long = 1L
)

/*
fun SellerEntity.toSeller(): Seller {
    return Seller(
        name,
    )
}

fun Seller.toSellerEntity(): SellerEntity {
    return SellerEntity(
        , name,
    )
}*/
