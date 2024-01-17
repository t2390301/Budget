package com.example.budget.di

import com.example.budget.DatabaseHelper
import com.example.budget.repository.DBRepository
import com.example.budget.view.fragments.planning.PlanningViewModel
import com.example.budget.viewmodel.ExpenseItemViewModel
import com.example.budget.viewmodel.ExportAndBackupViewModel
import com.example.budget.viewmodel.MainActivityViewModel
import com.example.budget.viewmodel.MainFragmentViewModel
import com.example.budget.viewmodel.SMSFragmentViewModel
import com.example.budget.viewmodel.SellerFragmentViewMode
import org.koin.dsl.module

val application = module {
    single {
        DatabaseHelper().apply {
            this.initDatabase(get())
        }
    }
}

val viewModels = module {
    factory { MainActivityViewModel(get()) }
    factory { MainFragmentViewModel(get()) }
    factory { SellerFragmentViewMode(get()) }
    factory { SMSFragmentViewModel(get()) }
    factory { ExportAndBackupViewModel(get()) }
    factory { ExpenseItemViewModel(get()) }
    /*factory { AccountFragmentViewModel(get()) }*/
    factory { PlanningViewModel(get()) }
}

val repository = module {
    single { DBRepository(get()) }
}
