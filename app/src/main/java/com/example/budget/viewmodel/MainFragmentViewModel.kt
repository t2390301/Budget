package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel(private val liveDataForViewToObserve: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {
    fun getLiveData() = liveDataForViewToObserve
}