package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val aplication = App()
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

    fun getSellers() {
        CoroutineScope(Dispatchers.IO).launch {
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

    fun getBankAccounts() {
        CoroutineScope(Dispatchers.IO).launch {
            bankAccountsAppState.value = AppState.Loading(true)
            try {
                bankAccountsAppState.value = AppState.Success(
                    bankAccountRepository.getBankAccounts()
                )
            } catch (e: Throwable) {
                bankAccountsAppState.value = AppState.Error(e)
            }
        }
    }

    fun updateSMSList(lastSMSDate: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            smsListAppState.value = AppState.Loading(true)
            val banksSMSAddress = bankRepository.getBanks().map { it.smsAddress }
            try {
                smsRepository.readSMSAfterData(lastSMSDate)
                    ?.filter { sms -> sms.sender in banksSMSAddress }?.let {
                        smsListAppState.value = AppState.Success(it)
                    }
            } catch (e: Throwable) {
                smsListAppState.value = AppState.Error(e)
            }

        }
    }

    fun saveSMSListToDB() {
        CoroutineScope(Dispatchers.IO).launch {
            (bankAccountAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBankAccounts(it)
            }
            (sellerAppState.value as AppState.Success).data?.let { smsDataMapper.updateSellers(it) }

            if (smsListAppState.value is AppState.Success) {
                (smsListAppState.value as AppState.Success).data?.let { listSMS ->
                    for (sms in listSMS.filter { it.isCashed == false }) {
                        val budgetEntryEntity = smsDataMapper.convertSMSToBudgetEntry(sms)
                        budgetEntryEntity?.let {
                            budgetEntryRepository.insertBudgetEntry(it)
                            sms.isCashed = true
                        }
                    }
                }
            }
        }
    }

}