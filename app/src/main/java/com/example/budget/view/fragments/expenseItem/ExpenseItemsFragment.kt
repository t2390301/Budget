package com.example.budget.view.fragments.expenseItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.budget.databinding.FragmentExpenseItemsBinding
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.domain.BudgetGroupWithAmount
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.ExpenseItemViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExpenseItemsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentExpenseItemsBinding? = null

    private val binding get() = _binding!!

    private var newBudgetGroupId = -1L

    val viewModel: ExpenseItemViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var budgetGroupWithAmount = mutableListOf<BudgetGroupWithAmount>()
        viewModel.budgetGroupsWithAmount = viewModel.getBudgetGroupWithAmount()
        if ((viewModel.budgetGroupsWithAmount.value is AppState.Success)) {
            budgetGroupWithAmount =
                (viewModel.budgetGroupsWithAmount.value as AppState.Success).data as MutableList<BudgetGroupWithAmount>
        }

        var sellers = mutableListOf<SellerEntity>()
        if (viewModel.sellersListLiveData.value is AppState.Success) {
            sellers =
                (viewModel.sellersListLiveData.value as AppState.Success).data as MutableList<SellerEntity>
        }

        var recyclerViewVisible = true

        val behavior = BottomSheetBehavior.from(binding.included.bottomSheetExpenseItemContainer)

        val adapter = ExpenseViewAdapter(budgetGroupWithAmount, sellers ){budgetGroup, sellersList ->
            with(binding.included) {
                newBudgetGroupId = budgetGroup.id
                editTextExpenseItem.editText?.setText(budgetGroup.name)
                editTextNote.editText?.setText(budgetGroup.description)
                val sellersString =
                    sellersList.filter{it.budgetGroupId == budgetGroup.id}
                        .map { it.name }.joinToString("\n")
                editTextMarkSms.editText?.setText(sellersString)
            }
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.expenseItemRecyclerView.visibility = View.GONE
        }

        binding.expenseItemRecyclerView.adapter = adapter

        binding.expenseItemRecyclerFab.setOnClickListener {
            if (recyclerViewVisible) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.expenseItemRecyclerView.visibility = View.GONE
                newBudgetGroupId = -1L
                recyclerViewVisible = false
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.expenseItemRecyclerView.visibility = View.VISIBLE
                recyclerViewVisible = true
            }

        }

        binding.included.saveButton.setOnClickListener {
            with(binding.included) {
                editTextExpenseItem.editText?.text?.let {
                    if (it.length > 0) {

                        val name = it.toString()
                        val description = editTextNote.editText?.text.toString()

                        insertBudgetGroup(
                            BudgetGroupEntity(
                                newBudgetGroupId,
                                name, description, null
                            )
                        )
                    }
                }
            }
        }




        viewModel.budgetGroupsWithAmount.observe(viewLifecycleOwner) {
            if (it is AppState.Success) {
                budgetGroupWithAmount = it.data as MutableList<BudgetGroupWithAmount>
                adapter.setData(budgetGroupWithAmount, sellers)
            }
        }

        viewModel.sellersListLiveData.observe(viewLifecycleOwner) {
            if (it is AppState.Success) {
                sellers = it.data as MutableList<SellerEntity>
                adapter.setData(budgetGroupWithAmount, sellers)
            }
        }
    }


    fun insertBudgetGroup(budgetGroupEntity: BudgetGroupEntity) {
        if (budgetGroupEntity.name.length > 0) {
            viewModel.updateBudgetGroupEntity(budgetGroupEntity)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}






