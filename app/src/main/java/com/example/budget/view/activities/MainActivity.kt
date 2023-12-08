package com.example.budget.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.budget.R
import com.example.budget.databinding.FragmentMainBinding
import com.example.budget.model.data.SmsDataMapper
import com.example.budget.model.data.SmsReader
import com.example.budget.view.fragments.main.MainFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentMainBinding
    lateinit var smsData: SmsReader
    lateinit var smsDataMapper: SmsDataMapper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        }


    }

}