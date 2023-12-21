package com.example.budget.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.budget.model.database.converters.BudgetGroupConverter
import com.example.budget.model.database.converters.DateConverter
import com.example.budget.model.database.converters.OperationTypeConverter
import com.example.budget.model.database.converters.StringListConverter
import com.example.budget.model.database.dao.BankAccountDao
import com.example.budget.model.database.dao.BankDao
import com.example.budget.model.database.dao.BudgetEntryDao
import com.example.budget.model.database.dao.BudgetGroupDao
import com.example.budget.model.database.dao.CombainTableDao
import com.example.budget.model.database.dao.PlanningNoteDao
import com.example.budget.model.database.dao.SellerDao
import com.example.budget.model.database.dao.SmsDataDao
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.database.entity.BankEntity
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.PlanningNoteEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.database.entity.SmsDataEntity

@Database(
    entities = [
        SmsDataEntity::class,
        BudgetGroupEntity::class,
        BudgetEntryEntity::class,
        BankAccountEntity::class,
        BankEntity::class,

        SellerEntity::class,
        PlanningNoteEntity::class
    ],

    version = 4

)
@TypeConverters(
    OperationTypeConverter::class,
    DateConverter::class,
    StringListConverter::class,
    BudgetGroupConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bank_table " +
                        "ADD COLUMN cardPanRegex VARCHAR(256) NOT NULL, " +
                        "ADD COLUMN sellerNameRegex VARCHAR(256) NOT NULL, " +
                        "ADD COLUMN operationAmountRegex VARCHAR(256) NOT NULL, " +
                        "ADD COLUMN balanceRegex VARCHAR(256) NOT NULL;")
                db.execSQL("ALTER TABLE sms_data_table " +
                        "DROP COLUMN bankAccountFound," +
                        "DROP COLUNM sellerFound;")
                db.execSQL("TRUNCATE TABLE bank_account_table")

            }
        }

        val MIGRATION_2_3 = object : Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE seller_table CONSTRAINT budgetGroupId ON DELETE SET_DEFAULT ")
            }
        }

        val MIGRATION_3_4 = object : Migration(3,4){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE seller_table CONSTRAINT budgetGroupId ON DELETE SET_DEFAULT ")
            }
        }

    }
    abstract fun smsDataDao(): SmsDataDao
    abstract fun budgetGroupEntityDao(): BudgetGroupDao
    abstract fun budgetEntryEntityDao(): BudgetEntryDao
    abstract fun bankDao(): BankDao

    abstract fun sellerDao(): SellerDao
    abstract fun bankAccountDao(): BankAccountDao
    abstract fun combainTableDao(): CombainTableDao

    abstract fun getPlanningNoteDao(): PlanningNoteDao

}




