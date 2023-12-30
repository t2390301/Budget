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

    version = 6

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
                        "DROP COLUMN sellerFound;")
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

        val MIGRATION_4_5 = object : Migration(4,5){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    sql = "ALTER TABLE bank_table " +
                            "ADD COLUMN bankImage INT NULL;"
                )
            }

        }

        val MIGRATION_5_6 = object : Migration(5,6){
            override fun migrate(db: SupportSQLiteDatabase) {
                with(db){
                    execSQL("CREATE TABLE bg_backup " +
                            "(id INTEGER NOT NULL, name TEXT NOT NULL, description TEXT NOT NULL, iconResId INTEGER NOT NULL, " +
                            "PRIMARY KEY (id))")
                    execSQL("INSERT INTO bg_backup SELECT id, name, description, iconResId FROM budget_group_table")
                    execSQL("DROP TABLE budget_group_table")
                    execSQL("ALTER TABLE bg_Backup RENAME TO budget_group_table")
                }
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




