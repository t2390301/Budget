package com.example.budget.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budget.App
import com.example.budget.R
import com.example.budget.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: BottomNavigationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomNavigationLayoutBinding.inflate(layoutInflater)
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