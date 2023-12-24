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

class SMSFragmentViewModel: ViewModel() {
    companion object{
        const val TAG = "SMSFragmentViewModel"
    }

    val application = App.app
    val dbRepository = DBRepository(application.getDatabaseHelper())
    val converter = Converters(dbRepository)

  //  var SMSLiveData = getAllSMS()

    private fun getAllSMS(): LiveData<List<SmsDataEntity>> {
        val liveDataSMS = MutableLiveData<List<SmsDataEntity>>()
        viewModelScope.launch {
           val smsList = dbRepository.getSMSList()
            if(!smsList.isEmpty()){
                liveDataSMS.value = smsList
            }
        }
        return liveDataSMS
    }

    var _smsDataList:MutableLiveData<MutableList<SmsData>>  = getAllSMSData()

    fun getAllSMSData(): MutableLiveData<MutableList<SmsData>>{
        var smsList :MutableList<SmsDataEntity>
        val liveData = MutableLiveData<MutableList<SmsData>>()
        viewModelScope.launch {
            val banks = dbRepository.getBankEntities()
            smsList = dbRepository.getSMSList().toMutableList()
            if(!smsList.isEmpty()){

                liveData.value = smsList.map { converter.smsDataEntityConverter(it, banks)}.toMutableList()
            }
        }
        return liveData
    }

    fun updateSMS(sms: SmsDataEntity) {                           //updateSMS: from broadcast receiver
        viewModelScope.launch {
            val banks = dbRepository.getBankEntities()
            val bankSenderList = banks.map { it.smsAddress }
            if (sms.sender in bankSenderList) {

                val smsList = _smsDataList.value
                smsList?.let {
                    it.add(converter.smsDataEntityConverter(sms, banks))
                    _smsDataList.value = it
                }
            }
        }
    }


}