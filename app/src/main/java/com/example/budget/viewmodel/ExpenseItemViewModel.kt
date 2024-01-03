package com.example.budget.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.App
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.domain.BudgetGroup
import com.example.budget.model.domain.BudgetGroupWithAmount
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.launch

class ExpenseItemViewModel : ViewModel() {

    val dbRepository = DBRepository(App.app.getDatabaseHelper())
    var budgetGroupsWithAmount =
        getBudgetGroupWithAmount() //MutableLiveData<AppState<List<BudgetGroupWithAmount>>>()

    fun getBudgetGroupWithAmount(): MutableLiveData<AppState<MutableList<BudgetGroupWithAmount>>> {
        var liveData = MutableLiveData<AppState<MutableList<BudgetGroupWithAmount>>>()
        liveData.value = AppState.Loading(true)
        viewModelScope.launch {
            val budgetGroupsList = dbRepository.getBudgetGroupWithAmount()
            if (!budgetGroupsList.isEmpty()) {
                liveData.value = AppState.Success(budgetGroupsList as MutableList<BudgetGroupWithAmount>)
            }
        }
        return liveData
    }

    var sellersListLiveData = getSellers()

    fun getSellers(): MutableLiveData<AppState<List<SellerEntity>>> {
        var sellersEntity = MutableLiveData<AppState<List<SellerEntity>>>()
        sellersEntity.value = AppState.Loading(true)

        viewModelScope.launch {
            sellersEntity.value = AppState.Success(dbRepository.getSellerEntities())
        }
        return sellersEntity
    }

    fun updateBudgetGroup(budgetGroup: BudgetGroup) {
        viewModelScope.launch {
            if (budgetGroupsWithAmount.value is AppState.Success) {

                dbRepository.insertBudgetGroupEntity(
                    BudgetGroupEntity(
                        0L,
                        budgetGroup.name,
                        budgetGroup.description,
                        budgetGroup.iconResId
                    )
                )
                val budgetGroupList = dbRepository.getBudgetGroupWithAmount()

                budgetGroupsWithAmount.value = AppState.Success(budgetGroupList.toMutableList())
            }
        }
    }


}
