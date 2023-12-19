package com.example.budget.view.fragments.exportandbackup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.budget.databinding.FragmentExportAndBackupBinding
import com.example.budget.databinding.FragmentSmsBinding
import com.example.budget.model.domain.BudgetEntry
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExportAndBackupFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentExportAndBackupBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainActivityViewModel by viewModels()  //Создать отдельную ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExportAndBackupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exportButton.setOnClickListener() {
            if (viewModel.budgetEntriesAppState.value is AppState.Success) {
                (viewModel.budgetEntriesAppState.value as AppState.Success<MutableList<BudgetEntry>>).data?.let {
                    convertBudgetEntriesToExcel(it)
                }
            }
        }
    }

    private fun convertBudgetEntriesToExcel(budgetEntries: List<BudgetEntry>) {

    }


}