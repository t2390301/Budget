package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.BankAccountRepository
import com.example.budget.repository.BankRepository
import com.example.budget.repository.BudgetEntryRepository
import com.example.budget.repository.BudgetGroupRepository
import com.example.budget.repository.SMSRepository
import com.example.budget.repository.SellerRepository
import com.example.budget.repository.SmsDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {
    val aplication = App.app
    val smsRepository = SMSRepository(App.app)
    val bankAccountRepository = BankAccountRepository(aplication.getDatabaseHelper())
    val bankRepository = BankRepository(aplication.getDatabaseHelper())
    val budgetEntryRepository = BudgetEntryRepository(aplication.getDatabaseHelper())
    val sellerRepository = SellerRepository(aplication.getDatabaseHelper())
    val budgetGroupRepository = BudgetGroupRepository(aplication.getDatabaseHelper())
    val smsDataRepository = SmsDataRepository(aplication.getDatabaseHelper())

    var bankAccountsAppState = MutableLiveData<AppState<List<BankAccount>>>()

    var sellersAppState = MutableLiveData<AppState<List<Seller>>>()

    val smsDataMapper = SmsDataMapper()

    val smsListAppState = MutableLiveData<AppState<List<SmsData>>>()
    val bankAccountAppState = MutableLiveData<AppState<List<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<List<Seller>>>()

    suspend fun getSellers() {
        withContext(Dispatchers.Main) {
            sellerAppState.value = AppState.Loading(true)
            try {
                sellerAppState.value = AppState.Success(
                    sellerRepository.getSellers()
                )
            } catch (e: Throwable) {
                sellerAppState.value = AppState.Error(e)
            }
        }
    }

    suspend fun getBankAccounts() {
        withContext(Dispatchers.Main) {
            bankAccountsAppState.value = AppState.Loading(true)
            try {
                val bankAccounts = bankAccountRepository.getBankAccounts()
                bankAccountsAppState.value = AppState.Success(
                    bankAccounts
                )
            } catch (e: Throwable) {
                bankAccountsAppState.value = AppState.Error(e)
            }
        }
    }

    fun updateSMSList(lastSMSDate: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            launch(Dispatchers.Main) { smsListAppState.value = AppState.Loading(true) }

            val banksSMSAddress = bankRepository.getBanks().map { it.smsAddress }
            try {
                smsRepository.readSMSAfterData(lastSMSDate)
                    ?.filter { sms -> sms.sender in banksSMSAddress }?.let {
                        smsListAppState.value = AppState.Success(it)
                    }
            } catch (e: Throwable) {
                launch(Dispatchers.Main) { smsListAppState.value = AppState.Error(e) }
            }

        }
    }

    fun saveSMSListToDB() {
        viewModelScope.launch(Dispatchers.Main) {
            getBankAccounts()
            getSellers()



            (bankAccountAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBankAccounts(it)
            }
            (sellerAppState.value as AppState.Success).data?.let { smsDataMapper.updateSellers(it) }

            if (smsListAppState.value is AppState.Success) {
                (smsListAppState.value as AppState.Success).data?.let { listSMS ->
                    for (sms in listSMS.filter { it.isCashed == false }) {
                        val budgetEntryEntity = smsDataMapper.convertSMSToBudgetEntry(sms)
                        budgetEntryEntity?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                budgetEntryRepository.insertBudgetEntry(
                                    it
                                )
                            }
                            sms.isCashed = true
                        }
                    }
                }
            }
        }

    }
}
