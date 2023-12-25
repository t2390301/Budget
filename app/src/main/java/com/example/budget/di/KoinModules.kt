package com.example.budget.di

import com.example.budget.model.utils.DatabaseHelper
import com.example.budget.repository.BankAccountRepository
import com.example.budget.repository.DBRepository
import com.example.budget.view.fragments.planning.PlanningViewModel
import com.example.budget.viewmodel.MainActivityViewModel
import com.example.budget.viewmodel.MainFragmentViewModel
import org.koin.dsl.module

object Di {
    val application = module {
        single { BankAccountRepository(get()) }
        single { DBRepository(get()) }
        single {
            DatabaseHelper().apply {
                this.initDatabase(get())
            }
        }
    }

    val mainScreen = module {
        factory { MainActivityViewModel(get()) }
        factory { MainFragmentViewModel(get()) }
        factory { PlanningViewModel(get()) }
    }


}
