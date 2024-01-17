package com.example.budget.view.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.budget.R
import com.example.budget.databinding.FragmentMainBinding
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.view.fragments.main.MainFragment
import com.example.budget.view.fragments.planning.PlanningFragment
import com.example.budget.view.fragments.sms.SMSFragment
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivityView"
    }

    private lateinit var binding: FragmentMainBinding
    lateinit var BudgetEntries: MutableLiveData<List<BudgetEntry>>
    private var broadcastReceiver: BroadcastReceiver? = null

    val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        var permissions = mutableListOf<String>()

        if (checkSelfPermission("android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            permissions.add("android.permission.READ_SMS")
        }
        if (checkSelfPermission("android.permission.RECEIVE_SMS") != PackageManager.PERMISSION_GRANTED) {
            permissions.add("android.permission.RECEIVE_SMS")
        }
        if (permissions.isNotEmpty()) {

            requestPermissions(permissions.toTypedArray(), 2)
        } else {
            smsActions()
        }




        viewModel.budgetEntriesAppState.observe(this) { budgets ->
            if (budgets is AppState.Success) {
                //Log.i(TAG, "onCreate: Success")
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                var isPerpermissionForAllGranted = true
                if (grantResults.size > 0 && permissions.size == grantResults.size) {
                    for (result in grantResults) {
                        isPerpermissionForAllGranted =
                            (result == PackageManager.PERMISSION_GRANTED) && isPerpermissionForAllGranted
                    }
                } else {
                    isPerpermissionForAllGranted = true
                }
                if (isPerpermissionForAllGranted) {
                    smsActions()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
    }

    private fun smsActions() {
        registerReceiver()

        viewModel.updateSMSList()
    }

    private fun registerReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                viewModel.updateSMSList()

            }

        }

        registerReceiver(broadcastReceiver, IntentFilter("com.example.budget"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.planning -> navigateTo(PlanningFragment())
            R.id.navigation_sms -> navigateTo(SMSFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateTo(fragment: BottomSheetDialogFragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.main_container, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate())
            super.onBackPressed()
    }
}