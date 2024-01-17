package com.example.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.database.entity.SmsDataEntity
import com.example.budget.model.domain.SmsData
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.launch

class SMSFragmentViewModel(private val dbRepository: DBRepository) : ViewModel() {
    companion object {
        const val TAG = "SMSFragmentViewModel"
    }

    val application = App.app
    val converter = Converters(dbRepository)



    var smsDataList: MutableLiveData<AppState<MutableList<SmsData>>> =
        getAllSMSData()


    private fun getAllSMSData(): MutableLiveData<AppState<MutableList<SmsData>>> {
        var smsList: MutableList<SmsDataEntity>
        val liveData = MutableLiveData<AppState<MutableList<SmsData>>>()

        viewModelScope.launch {
            val banks = dbRepository.getBankEntities()
            smsList = dbRepository.getSMSList().toMutableList()
            if (!smsList.isEmpty()) {
                liveData.value =
                    AppState.Success(smsList.map { converter.smsDataEntityConverter(it, banks) }
                        .toMutableList())
            }
        }
        return liveData
    }

    fun updateSMS(sms: SmsDataEntity) {                           //updateSMS: from broadcast receiver
        viewModelScope.launch {
            val banks = dbRepository.getBankEntities()
            val bankSenderList = banks.map { it.smsAddress }
            if (sms.sender in bankSenderList) {
                val smsList = (smsDataList.value as AppState.Success).data
                smsDataList.value = AppState.Loading(true)
                smsList?.let {
                    it.add(converter.smsDataEntityConverter(sms, banks))
                    smsDataList.value = AppState.Success(it)
                }
            }
        }
    }


}