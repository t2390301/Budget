package com.example.budget.view.fragments.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.budget.App
import com.example.budget.databinding.FragmentAccountsDetailBinding
import com.example.budget.model.constants.DEFAULT_BANK_IMAGE
import com.example.budget.model.domain.BankAccount
import com.example.budget.model.domain.CardType
import com.example.budget.viewmodel.AccountFragmentViewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ACCOUNT_ITEM_ID = "accountItemId"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountsDetailFragment : Fragment() {

    private var accountItemId: Long? = null

    private var _binding: FragmentAccountsDetailBinding? = null

    lateinit var bankAccount: BankAccount

    private val binding
        get() = _binding!!

    companion object {

        const val TAG = "AccountsDetailFragment"

        private val cardTypes = mutableListOf<String>(
            CardType.NOTYPE.toString(),
            CardType.CREDIT.toString(),
            CardType.DEBIT.toString()
        )

        @JvmStatic
        fun newInstance(accountId: Long) =
            AccountsDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ACCOUNT_ITEM_ID, accountId)
                }
            }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountItemId = it.getLong(ACCOUNT_ITEM_ID)
        }
    }

    private val viewModel:AccountFragmentViewModel by activityViewModels()

    private fun bind(account: BankAccount) {
        binding.apply {
            accountImgBank.setImageResource(account.bankImageId ?: DEFAULT_BANK_IMAGE)
            accountCardPan.text = account.cardPan
            accountBalance.setText(account.balance.toString(),TextView.BufferType.SPANNABLE)
            binding.accountTypeSpinner.adapter = spinnerAdapter
            accountTypeSpinner.setSelection(account.cardType.ordinal)
            accountTypeSpinner.onItemSelectedListener = itemSelectedListener
            accountCreditLimit.setText(account.cardLimit.toString(),TextView.BufferType.SPANNABLE)
            creditCardLimitFocus(account)
            saveItem.setOnClickListener {
                account.cardLimit = accountCreditLimit.text.toString().toDouble()
                account.balance = accountBalance.text.toString().toDouble()
                viewModel.updateBankAccountList(account)
            }
        }
    }


    private fun creditCardLimitFocus(account: BankAccount) {
        binding.apply {
            if (bankAccount.cardType == CardType.CREDIT) {
                accountCreditLimit.isFocusable = true
                accountCreditLimit.isFocusableInTouchMode = true
            } else {
                accountCreditLimit.isFocusable = false
                accountCreditLimit.isFocusableInTouchMode = false
            }
        }
    }

    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            bankAccount.cardType = CardType.values().get(position)
            creditCardLimitFocus(bankAccount)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountsDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveItem(accountItemId!!)?.observe(viewLifecycleOwner) {
            bankAccount = it
            bind(bankAccount)
        }

    }

    private val spinnerAdapter =
        App.app.applicationContext?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_item, cardTypes) }?.apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





