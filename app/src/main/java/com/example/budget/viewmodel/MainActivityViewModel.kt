package com.example.budget.viewmodel


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
import com.example.budget.model.utils.AppLogger
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.BankAccountRepository
import com.example.budget.repository.BankRepository
import com.example.budget.repository.DBRepository
import com.example.budget.repository.SMSRepository
import com.example.budget.repository.SellerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import java.util.Date


class MainActivityViewModel(
    private val dbRepository: DBRepository,
    private val bankRepository: BankRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val sellerRepository: SellerRepository
) : ViewModel() {
    private val application = app

    private val smsRepository = SMSRepository(application)

    var converter: Converters
    var smsDataMapper: SmsDataMapper
    val smsListAppState = MutableLiveData<AppState<MutableList<SmsData>>>()
    val budgetEntriesAppState = MutableLiveData<AppState<MutableList<BudgetEntry>>>()
    var bankAccountsAppState = MutableLiveData<AppState<MutableList<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<MutableList<Seller>>>()
    val banksAppState: MutableLiveData<AppState<MutableList<Bank>>>

    init {
        AppLogger.i("init : MainActivityViewModel")


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
            AppLogger.i("getSellers: ${sellerAppState.value!!.javaClass}; ")
        }
    }

    fun getBanks() {
        viewModelScope.launch {
            banksAppState.value = (AppState.Loading(true))
            try {
                AppLogger.i("getBanks: here")
                val banksList = bankRepository.getBanks()
                AppLogger.i("getBanks: Banks =  " + banksList.size)
                banksAppState.value = (AppState.Success(
                    bankRepository.getBanks()
                        .toMutableList()
                ))
                AppLogger.i(
                    "getBanks:  banksAppState is ${banksAppState.value is AppState.Success} "
                )
            } catch (e: Throwable) {
                banksAppState.value = (AppState.Error(e))
                e.printStackTrace()
                AppLogger.i("getBanks:  banksAppState is ${banksAppState.value is AppState.Error} ")
            }
        }
    }

    fun getBankAccounts() {

        viewModelScope.launch {
            bankAccountsAppState.value = (AppState.Loading(true))
            AppLogger.i(
                "getBankAccounts: is Loading ${bankAccountsAppState.value is AppState.Loading}"
            )
            try {
                var bankAccountsList = mutableListOf<BankAccountEntity>()
                dbRepository.getBankAccountEntities().let {
                    bankAccountsList = it.toMutableList()
                    AppLogger.i("getBankAccounts: size= ${it.size}")
                }
                /*   var bankAccountsList = mutableListOf<BankAccount>()
           var bankAccountsList = bankAccountRepository.getBankAccounts().toMutableList()
                AppLogger.i( "getBankAccounts: size= ${bankAccountsList.size}")*/

                for (account in bankAccountsList) {
                    (account).let {
                        AppLogger.i("getBankAccounts: ${it.bankId}; ${it.cardPan} ; ${it.bankId}")
                    }
                }

                bankAccountsAppState.value =
                    AppState.Success(
                        bankAccountsList    /*.toMutableList()*/
                            .map { converter.bankAccountEntityConverter(it)!! }.toMutableList()

                    )
                AppLogger.i(

                    "getBankAccounts: is Success ${bankAccountsAppState.value is AppState.Success}"
                )


            } catch (e: Throwable) {
                bankAccountsAppState.value = (AppState.Error(e))
                e.printStackTrace()
                AppLogger.i(
                    "getBankAccounts: is Error ${bankAccountsAppState.value is AppState.Error}"
                )
            }
        }
    }

    fun updateSMSList(lastSMSDate: Long) {

        viewModelScope.launch {

            smsListAppState.value = (AppState.Loading(true))
            AppLogger.i(

                "updateSMSList: smsList is Loading ${smsListAppState.value is AppState.Loading}"
            )
            val banksSMSAddress = dbRepository.getBankEntities().map { it.smsAddress }.distinct()

            try {
                AppLogger.i("updateSMSList: start sms reading")
                smsRepository.readSMSAfterData(lastSMSDate)
                    ?.filter { banksSMSAddress.contains(it.sender) }?.let {
                        smsListAppState.value = (AppState.Success(it.toMutableList()))
                        if (!it.isNullOrEmpty()) {
                            dbRepository.insertSMSDataEntityList(it.map { smsData ->
                                converter.smsDataConverter(
                                    smsData
                                )
                            })
                        }
                        AppLogger.i(

                            "updateSMSList: smsList is Success ${smsListAppState.value is AppState.Success} and smsList.size = ${it.size}"
                        )

                    }
            } catch (e: Throwable) {
                smsListAppState.postValue(AppState.Error(e))
            }

        }
        AppLogger.i("updateSMSList: ${Date(lastSMSDate)}")

    }

    fun saveSMSListToBudgetEntries() {
        viewModelScope.launch {
            budgetEntriesAppState.value = AppState.Loading(true)
            val budgetEntries = mutableListOf<BudgetEntry>()
            delay(8000)

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
                    AppLogger.i("saveSMSListToBudgetEntries: smsList.size = ${listSMS.size}")
                    for (sms in listSMS.filter { it.isCashed == false }) {

                        val budgetEntry = smsDataMapper.convertSMSToBudgetEntry(sms)
                        AppLogger.i(

                            "saveSMSListToBudgetEntries: returned budgetEntry is ${budgetEntry?.sellerName}"
                        )
                        budgetEntry?.let {

                            budgetEntries.add(budgetEntry)
                            converter.budgetEntryConverter(it)
                                ?.let { it1 -> dbRepository.insertBudgetEntryEntity(it1) }
                            sms.isCashed = true
                            if (!budgetEntries.isEmpty()) {
                                budgetEntriesAppState.value = AppState.Success(budgetEntries)
                            }
                        }
                    }
                }
            }

            AppLogger.i("saveSMSListToBudgetEntries: ${budgetEntries.size}")

            for (bde in budgetEntries) {
                AppLogger.i(

                    "saveSMSListToBudgetEntries: ${bde.bankSMSAdress}; ${bde.cardSPan}; ${bde.operationAmount}; ${bde.sellerName}"
                )
            }

        }

    }

}