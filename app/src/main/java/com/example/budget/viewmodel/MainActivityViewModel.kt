package com.example.budget.viewmodel


import android.annotation.SuppressLint
import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.budget.App.Companion.app

import com.example.budget.model.database.converters.Converters
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.DBRepository
import com.example.budget.repository.SMSRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("LogNotTimber")
class MainActivityViewModel(private val dbRepository: DBRepository) : ViewModel() {

    val application = app
    private val TAG = "MainActivityViewModel"
    val smsRepository = SMSRepository(application)


    var converter: Converters

    var smsDataMapper: SmsDataMapper

    val smsListAppState = MutableLiveData<AppState<MutableList<SmsData>>>()
    val budgetEntriesAppState = MutableLiveData<AppState<MutableList<BudgetEntry>>>()
    var bankAccountsAppState = MutableLiveData<AppState<MutableList<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<MutableList<Seller>>>()
    val banksAppState: MutableLiveData<AppState<MutableList<Bank>>>

    init {
        converter = Converters(dbRepository)
        smsDataMapper = SmsDataMapper(dbRepository)
        banksAppState = MutableLiveData()
        getBanks()
        getSellers()
        getBankAccounts()

    }

    fun getSellers() {
        viewModelScope.launch {
            sellerAppState.value = (AppState.Loading(true))
            try {
                sellerAppState.value = (AppState.Success(
                    dbRepository.getSellerEntities().map { converter.sellerEntityConverter(it)!! }
                        .toMutableList()
                ))
            } catch (e: Throwable) {
                e.printStackTrace()
                sellerAppState.postValue(AppState.Error(e))
            }
            Log.i(TAG, "getSellers: ${sellerAppState.value!!.javaClass}; ")

        }
    }

    fun getBanks() {
        viewModelScope.launch {
            banksAppState.value = (AppState.Loading(true))
            try {
                Log.i(TAG, "getBanks: here")
                val banksListEntities = dbRepository.getBankEntities()
                Log.i(TAG, "getBanks: Banks =  ${banksListEntities.size}")
                banksAppState.value = (AppState.Success(
                    dbRepository.getBankEntities().map { converter.bankEntityConverter(it) }
                        .toMutableList()
                ))
            } catch (e: Throwable) {
                banksAppState.value = (AppState.Error(e))
                e.printStackTrace()
            }
        }
    }

    fun getBankAccounts() {

        viewModelScope.launch {
            bankAccountsAppState.value = (AppState.Loading(true))
            try {

                var bankAccountsList = mutableListOf<BankAccountEntity>()
                dbRepository.getBankAccountEntities()?.let {
                    bankAccountsList = it.toMutableList()
                }


                bankAccountsAppState.value =
                    AppState.Success(
                        bankAccountsList
                            .map { converter.bankAccountEntityConverter(it)!! }.toMutableList()
                    )


            } catch (e: Throwable) {
                bankAccountsAppState.value = (AppState.Error(e))
                e.printStackTrace()

            }
        }
    }

    fun updateSMSList() {

        viewModelScope.launch {

            smsListAppState.value = (AppState.Loading(true))

            val banksSMSAddress = dbRepository.getBankEntities().map { it.smsAddress }.distinct()

            try {

                val lastSMSDate = dbRepository.getLastUnSafeSMSDate()
                smsRepository.readSMSafterDate(lastSMSDate)
                    ?.filter { banksSMSAddress.contains(it.sender) }?.let {
                        smsListAppState.value = (AppState.Success(it.toMutableList()))
                        if (!it.isEmpty()) {
                            dbRepository.insertSMSDataEntityList(it.map { smsData ->
                                converter.smsDataConverter(smsData)
                            })
                            convertSMSListToToBudgetEntries()
                        }

                    }
            } catch (e: Throwable) {
                Log.i(TAG, "updateSMSList: error")
                e.printStackTrace()
                smsListAppState.postValue(AppState.Error(e))
            }

        }

    }

    fun convertSMSListToToBudgetEntries() {
        viewModelScope.launch {
            budgetEntriesAppState.value = AppState.Loading(true)
            val budgetEntries = mutableListOf<BudgetEntry>()

            while ((banksAppState.value is AppState.Loading)
                or (banksAppState.value is AppState.Loading)
                or (bankAccountsAppState.value is AppState.Loading)
                or (sellerAppState.value is AppState.Loading)
            ) {
                delay(1000)
            }

            (banksAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBanks(it)
            }

            (bankAccountsAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBankAccounts(it)
            }

            (sellerAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateSellers(it)
            }
            if (smsListAppState.value is AppState.Success) {
                (smsListAppState.value as AppState.Success).data?.let { listSMS ->
                    for (sms in listSMS.filter { it.isCashed == false }) {
                        val budgetEntry = smsDataMapper.convertSMSToBudgetEntry(sms)
                        budgetEntry?.let {
                            budgetEntries.add(budgetEntry)
                            converter.budgetEntryConverter(it)
                                ?.let { it1 -> dbRepository.insertBudgetEntryEntity(it1) }
                            if (!budgetEntries.isEmpty()) {
                                budgetEntriesAppState.value = AppState.Success(budgetEntries)
                            }
                        }
                        sms.isCashed = true
                        dbRepository.updateSMS(converter.smsDataConverter(sms))
                    }
                }
            }

/*            Log.i(TAG, "saveSMSListToBudgetEntries: ${budgetEntries.size}")

            for (bde in budgetEntries) {
                Log.i(
                    TAG,
                    "saveSMSListToBudgetEntries: ${bde.bankSMSAdress}; ${bde.cardSPan}; ${bde.operationAmount}; ${bde.sellerName}"
                )
            }*/

        }

    }

}