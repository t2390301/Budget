package com.example.budget.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.budget.R
import com.example.budget.databinding.ActivityMainBinding
import com.example.budget.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        }
    }
}