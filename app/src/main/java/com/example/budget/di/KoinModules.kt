package com.example.budget.di

import com.example.budget.model.utils.DatabaseHelper
import com.example.budget.repository.BankAccountRepository
import com.example.budget.repository.BankRepository
import com.example.budget.repository.DBRepository
import com.example.budget.repository.PlanningNoteRepository
import com.example.budget.repository.SellerRepository
import com.example.budget.view.fragments.planning.PlanningViewModel
import com.example.budget.viewmodel.ExportAndBackupViewModel
import com.example.budget.viewmodel.MainActivityViewModel
import com.example.budget.viewmodel.MainFragmentViewModel
import org.koin.dsl.module

val application = module {
    single {
        DatabaseHelper().apply {
            this.initDatabase(get())
        }
    }
}

val budgetEntry = module {
    single { BankAccountRepository(get()) }
    single { BankRepository(get()) }
    single { SellerRepository(get()) }
    single { DBRepository(get()) }
    factory { MainActivityViewModel(get(), get(), get(), get()) }
    factory { MainFragmentViewModel(get()) }
}

val planningNote = module {
    single { PlanningNoteRepository(get()) }
    factory { PlanningViewModel(get()) }
    factory { ExportAndBackupViewModel(get()) }
}

val exportAndBackup = module {
    factory { ExportAndBackupViewModel(get()) }
}


