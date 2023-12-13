package com.example.budget.model.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.budget.model.domain.Seller

@Entity(
    tableName = "seller_table", foreignKeys = [ForeignKey(
        entity = BudgetGroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["budgetGroupId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SellerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val budgetGroupId: Long
)

fun SellerEntity.toSeller(): Seller {
    return Seller(
        id, name, budgetGroupId
    )
}

fun Seller.toSellerEntity(): SellerEntity {
    return SellerEntity(
        id, name, budgetGroupId
    )
}