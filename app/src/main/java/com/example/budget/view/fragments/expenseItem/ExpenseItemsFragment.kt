package com.example.budget.view.fragments.expenseItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budget.databinding.FragmentExpenseItemsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExpenseItemsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentExpenseItemsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseItemsBinding.inflate(inflater, container, false)
        return binding.root
    }
}