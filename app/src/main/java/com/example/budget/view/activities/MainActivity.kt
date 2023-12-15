package com.example.budget.view.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.budget.R
import com.example.budget.databinding.FragmentMainBinding
import com.example.budget.model.constants.LAST_SAVED_SMS_Date
import com.example.budget.view.fragments.main.MainFragment
import com.example.budget.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission("android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("android.permission.READ_SMS"), 2)
        }

        val sharedPref = getPreferences(Context.MODE_PRIVATE)


        val lastSMSDate:Long = sharedPref.getLong(LAST_SAVED_SMS_Date, 0)

        val viewModel: MainActivityViewModel by viewModels()
        viewModel.updateSMSList(lastSMSDate = lastSMSDate)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        }


    }

}