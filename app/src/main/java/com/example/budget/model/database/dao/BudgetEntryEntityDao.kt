package com.example.budget.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.budget.model.database.BudgetEntryEntity
import com.example.budget.model.database.BudgetGroupEntity
import com.example.budget.model.domain.OperationType
import java.util.Date

@Dao
interface BudgetEntryEntityDao {
    @Query("INSERT INTO budget_entry_table (id,smsId, date,operationType, operationAmount, transactionSource,note,cardPan) VALUES(:id,:smsId,:date,:operationType,:operationAmount,:transactionSource,:note,:cardPan)")
    suspend fun nativeInsert(
        id: Long,
        smsId: Long,
        date: Date,
        operationType: OperationType,
        operationAmount: Double,
        transactionSource: String,
        note: String,
        cardPan: String
    )

    @Query("SELECT * FROM budget_entry_table")
    suspend fun all(): List<BudgetEntryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BudgetEntryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<BudgetEntryEntity>)

    @Update
    suspend fun update(entity: BudgetEntryEntity)

    @Delete
    suspend fun delete(entity: BudgetEntryEntity)
}