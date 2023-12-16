package com.example.budget.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.App.Companion.app
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.DBRepository
import com.example.budget.repository.SMSRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    //val aplication = App()

    private val TAG = "MainActivityViewModel"
    val smsRepository = SMSRepository(App.app)
    private lateinit var dbRepository: DBRepository

    var converter :Converters

    var smsDataMapper: SmsDataMapper

    val smsListAppState = MutableLiveData<AppState<MutableList<SmsData>>>()
    val budgetEntriesAppState = MutableLiveData<AppState<MutableList<BudgetEntry>>>()
    var bankAccountsAppState = MutableLiveData<AppState<MutableList<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<MutableList<Seller>>>()
    lateinit var banksAppState : MutableLiveData<AppState<MutableList<Bank>>>

    init{
        dbRepository = DBRepository(app.getDatabaseHelper())
        converter = Converters(dbRepository)
        smsDataMapper = SmsDataMapper(dbRepository)
        banksAppState = MutableLiveData()
        getBanks()
        getSellers()
        Log.i(TAG, "init : MainActivityViewModel")

        getBankAccounts()

    }

    fun getSellers() {
        viewModelScope.launch {
            sellerAppState.postValue(AppState.Loading(true))
            try {
                sellerAppState.postValue( AppState.Success(
                    dbRepository.getSellerEntities().map { converter.sellerEntityConverter(it)!! }
                        .toMutableList()
                ))
            } catch (e: Throwable) {
                sellerAppState.postValue( AppState.Error(e))
            }
        }
    }

    fun getBanks() {
        viewModelScope.launch(Dispatchers.IO) {
            banksAppState.postValue(AppState.Loading(true))
            try {
                banksAppState.postValue( AppState.Success(
                    dbRepository.getBankEntities().map { converter.bankEntityConverter(it) }
                        .toMutableList()
                ))
            } catch (e: Throwable) {
                banksAppState.postValue(AppState.Error(e))
            }
        }
    }

    fun getBankAccounts() {
        viewModelScope.launch(Dispatchers.IO) {
            bankAccountsAppState.postValue(AppState.Loading(true))
            Log.i(TAG, "getBankAccounts: is Loading ${bankAccountsAppState.value is AppState.Loading}")
            try {
                bankAccountsAppState.postValue( AppState.Success(
                    dbRepository.getBankAccountEntities()
                        .map { converter.bankAccountEntityConverter(it)!! }.toMutableList()))
                Log.i(TAG, "getBankAccounts: is Success ${bankAccountsAppState.value is AppState.Success}")
            } catch (e: Throwable) {
                bankAccountsAppState.postValue( AppState.Error(e))
            }
        }
    }

    fun updateSMSList(lastSMSDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            smsListAppState.postValue( AppState.Loading(true))
            val banksSMSAddress = dbRepository.getBankEntities().map { it.smsAddress }
            try {
                smsRepository.readSMSAfterData(lastSMSDate)
                    ?.filter { sms -> sms.sender in banksSMSAddress }?.let {
                        smsListAppState.postValue( AppState.Success(it.toMutableList()))
                    }
            } catch (e: Throwable) {
                smsListAppState.postValue( AppState.Error(e))
            }

        }
    }

    fun saveSMSListToBudgetEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            (bankAccountsAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBankAccounts(it)
            }

            (banksAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBanks(it)
            }
            (sellerAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateSellers(it)
            }

            (bankAccountsAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBankAccounts(it)
            }

            if (smsListAppState.value is AppState.Success) {
                (smsListAppState.value as AppState.Success).data?.let { listSMS ->
                    for (sms in listSMS.filter { it.isCashed == false }) {

                        val budgetEntry = smsDataMapper.convertSMSToBudgetEntry(sms)
                        budgetEntry?.let {
                            (budgetEntriesAppState.value as AppState.Success).data.let { listBudgetEntries ->
                                listBudgetEntries?.add(budgetEntry)
                            }

                            converter.budgetEntryConverter(it)
                                ?.let { it1 -> dbRepository.insertBudgetEntryEntity(it1) }
                            sms.isCashed = true
                        }
                    }
                }
            }
        }
    }



}