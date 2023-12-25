package com.example.budget.view.fragments.accounts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.budget.databinding.FragmentAccountsBinding
import com.example.budget.model.domain.BankAccount
import com.example.budget.viewmodel.AccountFragmentViewModel
import com.example.budget.viewmodel.AppState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountsFragment : BottomSheetDialogFragment() {

    companion object{
        const val TAG= "AccountsFragment"
    }
    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    val viewModel: AccountFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bankAccountsList= mutableListOf<BankAccount>()

        if (viewModel.bankAccountsLiveData.value is AppState.Success) {
            (viewModel.bankAccountsLiveData.value as AppState.Success).data?.let { bankAccountsList= it.toMutableList() }
        }

        val accountAdapter = AccountsFragmentAdapter(bankAccountsList)

        binding.accountRecyclerView.adapter = accountAdapter

        viewModel.bankAccountsLiveData.observe(viewLifecycleOwner){appState ->
            when (appState) {
                is AppState.Success -> {
                    Log.i(TAG, "onViewCreated: App.Succeess")
                    appState.data?.let { accountAdapter.setData(it) }
                }
                is AppState.Loading ->{
                    Log.i(TAG, "onViewCreated: App.Loading")

                }
                is AppState.Error -> {

                }
            }
        }
    }
}