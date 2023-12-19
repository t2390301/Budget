package com.example.budget.view.fragments.exportandbackup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budget.databinding.FragmentExportAndBackupBinding
import com.example.budget.databinding.FragmentSmsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExportAndBackupFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentExportAndBackupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExportAndBackupBinding.inflate(inflater, container, false)
        return binding.root
    }
}