package com.example.budget.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budget.App
import com.example.budget.R
import com.example.budget.databinding.FragmentMainBinding
import com.example.budget.model.data.BankCard
import com.example.budget.model.data.Seller
import com.example.budget.model.database.BudgetEntryEntity
import com.example.budget.model.domain.OperationType
import com.example.budget.model.domain.TransactionSource
import com.example.budget.model.data.SMSData
import com.example.budget.model.data.SMSDataMapper
import com.example.budget.view.fragments.main.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentMainBinding
    lateinit var smsData: SMSData
    lateinit var smsDataMapper: SMSDataMapper

    companion object {
        val sellers = arrayListOf(
            Seller("METRO.SPB", TransactionSource.ТРАНСПОРТ),
            Seller("APTECHNOE", TransactionSource.ПРОДУКТЫ),
            Seller("DIXI-78788D", TransactionSource.ПРОДУКТЫ),
            Seller("BUSHE", TransactionSource.РАЗВЛЕЧЕНИЯ),
            Seller("bilet.nspk", TransactionSource.ТРАНСПОРТ)
        )

        val bankCards = arrayListOf(
            BankCard("Tinkoff", "*0345", 0.0)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        }
        val helper = (application as App).getDatabaseHelper()
        lifecycleScope.launch(Dispatchers.IO) {
            helper.getBudgetEntryEntityDao().insert(
                BudgetEntryEntity(
                    smsId = 1,
                    date = Date(),
                    operationType = OperationType.INCOME,
                    operationAmount = 100.0,
                    transactionSource = "FOOD",
                    note = "",
                    cardPan = "*1234"
                )
            )
            val entities = helper.getBudgetEntryEntityDao().getAll()
            var expenseAmount = 0.0
            var incomeAmount = 0.0
            entities.filter { it.operationType == OperationType.EXPENSE }
                .forEach { expenseAmount += it.operationAmount }
            entities.filter { it.operationType == OperationType.INCOME }
                .forEach { incomeAmount += it.operationAmount }

            lifecycleScope.launch {
                Toast.makeText(
                    this@MainActivity,
                    "Income: $incomeAmount\nExpense: $expenseAmount",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}