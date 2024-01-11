package com.example.budget.view.fragments.sellers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.budget.databinding.FragmentSellersBinding
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.domain.Seller
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.SellerFragmentViewMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SellersFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSellersBinding? = null

    val binding: FragmentSellersBinding
        get() = _binding!!

    val viewModel: SellerFragmentViewMode by activityViewModels()

    var sellersList = mutableListOf<Seller>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSellersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var budgetGroups: List<BudgetGroupEntity> = listOf()
        viewModel.updateBudgetGroups()
        viewModel.updateSellers()
        val sellersAdapter =
            SellerFragmentAdapter(sellersList, budgetGroups) {
                viewModel.updateSeller(it)

            }

        binding.sellersRecyclerView.adapter = sellersAdapter

        viewModel.budgetGroupEntityLiveData.observe(viewLifecycleOwner) { appState ->
            if (appState != null && appState is AppState.Success) {
                appState.data?.let {
                    budgetGroups = it

                    sellersAdapter.setGroups(budgetGroups)
                }
            }
        }

        viewModel.sellersViewModel.observe(viewLifecycleOwner) { appState ->
            if (appState != null && appState is AppState.Success) {
                appState.data?.let {
                    sellersList = it
                    sellersAdapter.setData(sellersList)
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        viewModel.sellersViewModel.value = AppState.Success(this.sellersList)
        viewModel.updateSellersEntity()
        super.onStop()

    }

    companion object {
        const val TAG = "SellersFragment"
    }

}