package com.example.budget.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App.Companion.app
import com.example.budget.model.utils.AppLogger
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.database.entity.BankAccountEntity
import com.example.budget.model.domain.Bank
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.model.domain.Seller
import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.BankAccountRepository
import com.example.budget.repository.BankRepository
import com.example.budget.repository.DBRepository
import com.example.budget.repository.SMSRepository
import com.example.budget.repository.SellerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date


class MainActivityViewModel : ViewModel() {
    val application = app
    private val TAG = "MainActivityViewModel"

    val smsRepository = SMSRepository(application)
    val dbRepository = DBRepository(application.getDatabaseHelper())


    val bankAccountRepository = BankAccountRepository(application.getDatabaseHelper())
    val bankRepository = BankRepository(application.getDatabaseHelper())
    val sellerRepository = SellerRepository(application.getDatabaseHelper())



    var converter: Converters
    var smsDataMapper: SmsDataMapper
    val smsListAppState = MutableLiveData<AppState<MutableList<SmsData>>>()
    val budgetEntriesAppState = MutableLiveData<AppState<MutableList<BudgetEntry>>>()
    var bankAccountsAppState = MutableLiveData<AppState<MutableList<BankAccount>>>()
    val sellerAppState = MutableLiveData<AppState<MutableList<Seller>>>()
    val banksAppState: MutableLiveData<AppState<MutableList<Bank>>>

    init {
      //  Log.i(TAG, "init : MainActivityViewModel")
        AppLogger.i( "init : MainActivityViewModel")


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

            //    Log.i(TAG, "sellers size =  ${(sellerAppState.value as AppState.Success).data?.size}")
        }
    }

    fun getBanks() {
        viewModelScope.launch {
            banksAppState.value = (AppState.Loading(true))
            try {
                Timber.tag(TAG).i("getBanks: here")
                val banksList = bankRepository.getBanks()
                Timber.tag(TAG).i("getBanks: Banks =  " + banksList.size)
                banksAppState.value = (AppState.Success(
                    bankRepository.getBanks()
                        .toMutableList()
                ))
                Log.i(
                    TAG,
                    "getBanks:  banksAppState is ${banksAppState.value is AppState.Success} "
                )
            } catch (e: Throwable) {
                banksAppState.value = (AppState.Error(e))
                e.printStackTrace()
                Log.i(TAG, "getBanks:  banksAppState is ${banksAppState.value is AppState.Error} ")
            }
        }
    }

    fun getBankAccounts() {

        viewModelScope.launch {
            bankAccountsAppState.value = (AppState.Loading(true))
            Log.i(
                TAG,
                "getBankAccounts: is Loading ${bankAccountsAppState.value is AppState.Loading}"
            )
            try {
                var bankAccountsList = mutableListOf<BankAccountEntity>()
                dbRepository.getBankAccountEntities()?.let {
                    bankAccountsList = it.toMutableList()
                    Log.i(TAG, "getBankAccounts: size= ${it.size}")
                }
                /*   var bankAccountsList = mutableListOf<BankAccount>()
           var bankAccountsList = bankAccountRepository.getBankAccounts().toMutableList()
                Log.i(TAG, "getBankAccounts: size= ${bankAccountsList.size}")*/

            for (account in bankAccountsList) {
                (account)?.let {
                    Log.i(TAG, "getBankAccounts: ${it.bankId}; ${it.cardPan} ; ${it.bankId}")
                }
            }

            bankAccountsAppState.value =
                AppState.Success(
                    bankAccountsList    /*.toMutableList()*/
                        .map { converter.bankAccountEntityConverter(it)!! }.toMutableList()

                )
            Log.i(
                TAG,
                "getBankAccounts: is Success ${bankAccountsAppState.value is AppState.Success}"
            )


        } catch (e: Throwable) {
            bankAccountsAppState.value = (AppState.Error(e))
            e.printStackTrace()
            Log.i(
                TAG,
                "getBankAccounts: is Error ${bankAccountsAppState.value is AppState.Error}"
            )
        }
    }
}

fun updateSMSList(lastSMSDate: Long) {

    viewModelScope.launch {

        smsListAppState.value = (AppState.Loading(true))
        Log.i(
            TAG,
            "updateSMSList: smsList is Loading ${smsListAppState.value is AppState.Loading}"
        )
        val banksSMSAddress = dbRepository.getBankEntities().map { it.smsAddress }.distinct()

        try {
            Log.i(TAG, "updateSMSList: start sms reading")
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
                    Log.i(
                        TAG,
                        "updateSMSList: smsList is Success ${smsListAppState.value is AppState.Success} and smsList.size = ${it.size}"
                    )

                }
        } catch (e: Throwable) {
            smsListAppState.postValue(AppState.Error(e))
        }

    }
    Log.i(TAG, "updateSMSList: ${Date(lastSMSDate)}")

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
                Log.i(TAG, "saveSMSListToBudgetEntries: smsList.size = ${listSMS.size}")
                for (sms in listSMS.filter { it.isCashed == false }) {

                    val budgetEntry = smsDataMapper.convertSMSToBudgetEntry(sms)
                    Log.i(
                        TAG,
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

        Log.i(TAG, "saveSMSListToBudgetEntries: ${budgetEntries.size}")

        for (bde in budgetEntries) {
            Log.i(
                TAG,
                "saveSMSListToBudgetEntries: ${bde.bankSMSAdress}; ${bde.cardSPan}; ${bde.operationAmount}; ${bde.sellerName}"
            )
        }

    }

}

}