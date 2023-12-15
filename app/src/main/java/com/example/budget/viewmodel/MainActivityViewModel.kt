package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budget.App
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


class MainActivityViewModel() : ViewModel() {
    val aplication = App()
    val smsRepository = SMSRepository(App.app)
    lateinit var dbRepository: DBRepository

    lateinit var converter :Converters

    lateinit var smsDataMapper: SmsDataMapper

    val smsListAppState = MutableLiveData<AppState<MutableList<SmsData>>>()
    val budgetEntriesAppState = MutableLiveData<AppState<MutableList<BudgetEntry>>>()
    var bankAccountsAppState = MutableLiveData<AppState<MutableList<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<MutableList<Seller>>>()
    val banksAppState = MutableLiveData<AppState<MutableList<Bank>>>()

    init{
        dbRepository = DBRepository(aplication.getDatabaseHelper())
        converter = Converters(dbRepository)
        smsDataMapper = SmsDataMapper(dbRepository)
        getBanks()
        getSellers()
        getBankAccounts()

    }

    fun getSellers() {
        CoroutineScope(Dispatchers.IO).launch {
            sellerAppState.value = AppState.Loading(true)
            try {
                sellerAppState.value = AppState.Success(
                    dbRepository.getSellerEntities().map { converter.sellerEntityConverter(it)!! }
                        .toMutableList()
                )
            } catch (e: Throwable) {
                sellerAppState.value = AppState.Error(e)
            }
        }
    }

    fun getBanks() {
        CoroutineScope(Dispatchers.IO).launch {
            banksAppState.value = AppState.Loading(true)
            try {
                banksAppState.value = AppState.Success(
                    dbRepository.getBankEntities().map { converter.bankEntityConverter(it) }.toMutableList()
                )
            } catch (e: Throwable) {
                banksAppState.value = AppState.Error(e)
            }
        }
    }

    fun getBankAccounts() {
        CoroutineScope(Dispatchers.IO).launch {
            bankAccountsAppState.value = AppState.Loading(true)
            try {
                bankAccountsAppState.value = AppState.Success(
                    dbRepository.getBankAccountEntities()
                        .map { converter.bankAccountEntityConverter(it)!! }.toMutableList())
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
                        smsListAppState.value = AppState.Success(it.toMutableList())
                    }
            } catch (e: Throwable) {
                smsListAppState.value = AppState.Error(e)
            }

        }
    }

    fun saveSMSListToBudgetEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            (banksAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBanks(it)
            }
            (bankAccountsAppState.value as AppState.Success).data?.let {
                smsDataMapper.updateBankAccounts(it)
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