package com.example.budget.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.budget.R
import com.example.budget.databinding.ActivityMainBinding
import com.example.budget.databinding.BottomNavigationLayoutBinding
import com.example.budget.view.fragments.main.BottomNavigationDrawerFragment
import com.example.budget.view.fragments.main.MainFragment

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
    }

}