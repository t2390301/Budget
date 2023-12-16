package com.example.budget.view.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.budget.R
import com.example.budget.databinding.FragmentMainBinding
import com.example.budget.model.constants.LAST_SAVED_SMS_Date
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.view.fragments.main.MainFragment
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }
    private lateinit var binding: FragmentMainBinding
    lateinit var BudgetEntries: MutableLiveData<List<BudgetEntry>>
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission("android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("android.permission.READ_SMS"), 2)
        }

        val sharedPref = getPreferences(Context.MODE_PRIVATE)


        val lastSMSDate: Long = sharedPref.getLong(LAST_SAVED_SMS_Date, 0)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainActivityViewModel::class.java)

        viewModel.updateSMSList(lastSMSDate = lastSMSDate)
        viewModel.saveSMSListToBudgetEntries()

        viewModel.budgetEntriesAppState.observe(this){budgets ->
            if(budgets is AppState.Success){
                Log.i(TAG, "onCreate: Success")
                budgets.data?.let { list ->
                    for (budget in list) {
                        Log.i(
                            TAG,
                            "onCreate: ${budget.date} ; ${budget.cardSPan} ; ${budget.sellerName} ; ${budget.operationAmount}"
                        )

                    }
                }
            }

        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        }


    }

}