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

    var SMSListLiveData = getAllSMS()

    fun getAllSMS(): LiveData<List<SmsData>>{
        var smsList :List<SmsDataEntity> = listOf()
        val liveData = MutableLiveData<List<SmsData>>()
        viewModelScope.launch {
            smsList = dbRepository.getSMSList()
            if(!smsList.isEmpty()){
                liveData.value = smsList.map { converter.smsDataEntityConverter(it)}
            }
        }
        return liveData
    }

}