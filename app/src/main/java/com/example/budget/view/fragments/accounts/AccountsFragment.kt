package com.example.budget.view.fragments.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budget.databinding.FragmentAccountsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }
}