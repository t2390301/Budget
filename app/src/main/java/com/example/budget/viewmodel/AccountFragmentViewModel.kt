package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budget.DatabaseHelper
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.domain.BankAccount
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.launch

class AccountFragmentViewModel(/*private val dbRepository: DBRepository*/) : ViewModel() {

    companion object {
        const val TAG = "AccountFragmentViewModel"
    }
    private val dbRepository = DBRepository(DatabaseHelper())
    val converters = Converters(dbRepository)

    var bankAccountsLiveData: MutableLiveData<AppState<MutableList<BankAccount>>>
            = setBankAccountList()

    fun retrieveItem(id: Long): MutableLiveData<BankAccount>? {

        val  liveData = MutableLiveData<BankAccount>()
        viewModelScope.launch {
            dbRepository.getBankAccountEntityById(id)?.let {
                liveData.value = converters.bankAccountEntityConverter(it)
            }
        }
        return liveData
    }

    private fun setBankAccountList(): MutableLiveData<AppState<MutableList<BankAccount>>> {
        var liveData = MutableLiveData<AppState<MutableList<BankAccount>>>()
        viewModelScope.launch {
            val bankAccountList = dbRepository.getBankAccountEntities()
                .map { it?.let { item -> converters.bankAccountEntityConverter(item) } }

            if (!bankAccountList.isNullOrEmpty()) {

                liveData.value = AppState.Success(bankAccountList as MutableList<BankAccount>)
            }
        }
        return liveData
    }

    fun updateBankAccountList(account: BankAccount) {
        viewModelScope.launch {
            converters.bankAccountConverter(account)?.let {
                bankAccountsLiveData.value = AppState.Loading(true)
                dbRepository.updatebankAccountEntity(it)
                val bankAccountList = dbRepository.getBankAccountEntities()
                    .map { it?.let { item -> converters.bankAccountEntityConverter(item) } }

                if (!bankAccountList.isNullOrEmpty()) {
                    bankAccountsLiveData.value = AppState.Success(bankAccountList as MutableList<BankAccount>)
                }
            }
        }
    }

}

class AccountFragmentViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountFragmentViewModel::class.java)){

            return AccountFragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}



