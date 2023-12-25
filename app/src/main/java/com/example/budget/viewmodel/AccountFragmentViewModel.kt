package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.domain.BankAccount
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.launch

class AccountFragmentViewModel : ViewModel() {

    companion object {
        const val TAG = "AccountFragmentViewModel"
    }

    val dbRepository = DBRepository(App.app.getDatabaseHelper())
    val converters = Converters(dbRepository)

    var bankAccountsLiveData: MutableLiveData<AppState<List<BankAccount>>>
            = setBankAccountList()

    private fun setBankAccountList(): MutableLiveData<AppState<List<BankAccount>>> {
        var liveData = MutableLiveData<AppState<List<BankAccount>>>()
        viewModelScope.launch {
            val bankAccountList = dbRepository.getBankAccountEntities()
                .map { it?.let { item -> converters.bankAccountEntityConverter(item) } }

            if (!bankAccountList.isNullOrEmpty()) {
                liveData.value = AppState.Success(bankAccountList as List<BankAccount>)
            }
        }
        return liveData
    }
}



