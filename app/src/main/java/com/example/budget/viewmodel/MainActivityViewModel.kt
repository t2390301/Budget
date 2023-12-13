package com.example.budget.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budget.App
import com.example.budget.model.database.converters.bankAccountEntityConverter
import com.example.budget.model.database.converters.budgetEntryConverter
import com.example.budget.model.database.converters.sellerEntityConverter
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.DBRepository
import com.example.budget.repository.SMSRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel() {
    val aplication = App()
    val smsRepository = SMSRepository(App.app)
    val dbRepository = DBRepository(aplication.getDatabaseHelper())

    var bankAccountsAppState = MutableLiveData<AppState<List<BankAccount>>>()

    var sellersAppState = MutableLiveData<AppState<List<Seller>>>()

    val smsDataMapper = SmsDataMapper(dbRepository)

    val smsListAppState = MutableLiveData<AppState<List<SmsData>>>()
    val bankAccountAppState = MutableLiveData<AppState<List<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<List<Seller>>>()



    fun getSellers() {
        CoroutineScope(Dispatchers.IO).launch {
            sellerAppState.value = AppState.Loading(true)
            try {
                sellerAppState.value = AppState.Success(
                    dbRepository.getSellerEntities().map { sellerEntityConverter(it) }
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
                    dbRepository.getBankAccountEntities().map { bankAccountEntityConverter(it) })
            } catch (e: Throwable) {
                bankAccountsAppState.value = AppState.Error(e)
            }
        }
    }

    fun updateSMSList(lastSMSDate: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            smsListAppState.value = AppState.Loading(true)
            val banksSMSAddress = dbRepository.getBankEntities().map { it.smsAddress }
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
                            dbRepository.insertBudgetEntryEntity(budgetEntryConverter(it))
                            sms.isCashed = true
                        }
                    }
                }
            }
        }
    }

}