package com.example.budget.view.fragments.exportandbackup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.budget.databinding.FragmentExportAndBackupBinding
import com.example.budget.model.domain.CombainBudgetEntry
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.ExportAndBackupViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExportAndBackupFragment : BottomSheetDialogFragment() {
    companion object{
        const val TAG = "ExportAndBackupFragment"

    }
    private var _binding: FragmentExportAndBackupBinding? = null
    private val binding get() = _binding!!

    val viewModel: ExportAndBackupViewModel by viewModels()

    val budgetEntriesLiveData = MutableLiveData<AppState<MutableList<CombainBudgetEntry>>>()
    /*    get() {
             return viewModel.getBudgetEntitiesForExcel()
        }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExportAndBackupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val budgetEntriesLiveData = viewModel.getBudgetEntitiesForExcel()

        binding.exportButton.setOnClickListener() {
           viewModel.updateBudgetEntitiesForExcel()
            convertBudgetEntriesToExcel()
        }
    }

    private fun convertBudgetEntriesToExcel() {
            viewModel._budgetEntitiesListForExcel.observe(viewLifecycleOwner) { appState ->
                if (appState is AppState.Success) {
                    val listBudgetEntities = (appState as AppState.Success).data
                    if (!listBudgetEntities.isNullOrEmpty()) {
                        viewModel.convertBudgetEntriesToExcel(listBudgetEntities)
                        openSendActivity(viewModel.getUri())
                    } else {
                        Toast.makeText(activity, "No Data For Excel", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun openSendActivity(fileUri: Uri?){
        if (fileUri != null){
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("application/xls")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share file report.xls ")
            intent.putExtra(Intent.EXTRA_STREAM, fileUri)
            context?.startActivity(intent)
        } else {
            Toast.makeText(activity, "File do not found", Toast.LENGTH_SHORT).show()
        }
    }


}