package com.example.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.domain.Seller
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.launch

class SellerFragmentViewMode : ViewModel() {

    val dbRepository = DBRepository(App.app.getDatabaseHelper())
    val converter = Converters(dbRepository)

    var sellersViewModel: MutableLiveData<AppState<MutableList<Seller>>> = getSellersLiveData()

    var seller = MutableLiveData<Seller> ()

    var budgetGroupEntityLiveData : LiveData<AppState<List<BudgetGroupEntity>>> = getBudgetEntityList()

    private fun getBudgetEntityList(): LiveData<AppState<List<BudgetGroupEntity>>> {
        var liveData: MutableLiveData<AppState<List<BudgetGroupEntity>>> = MutableLiveData(AppState.Loading(true))

        viewModelScope.launch {
            val budgetGroupEntities = dbRepository.getBudgetGroupEntities()
            if (!budgetGroupEntities.isNullOrEmpty()){
                liveData.value = AppState.Success(budgetGroupEntities)
            }
        }
        return liveData
    }

    private fun getSellersLiveData(): MutableLiveData<AppState<MutableList<Seller>>> {
        var sellersLiveData: MutableLiveData<AppState<MutableList<Seller>>> =
            MutableLiveData(AppState.Loading(true))
        viewModelScope.launch {
            val sellersList = dbRepository.getSellerEntities()
            if (!sellersList.isNullOrEmpty()) {
                sellersLiveData.value =
                    AppState.Success(
                        sellersList.map { converter.sellerEntityConverter(it) } as MutableList<Seller>)
            }
        }
        return sellersLiveData
    }

    fun updateSeller(position : Int){
        if (sellersViewModel.value is AppState.Success) {
            seller.value = (sellersViewModel.value as AppState.Success<MutableList<Seller>>).data?.get(position)
        }
    }


}
