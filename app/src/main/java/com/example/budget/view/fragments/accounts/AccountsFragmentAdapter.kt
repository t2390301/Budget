package com.example.budget.view.fragments.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemAccountFragmentBinding
import com.example.budget.model.constants.DEFAULT_BANK_IMAGE
import com.example.budget.model.domain.BankAccount

class AccountsFragmentAdapter(private var bankAccountsList: List<BankAccount>) : RecyclerView.Adapter<AccountsFragmentAdapter.BankAccountViewHolder>(){
    class BankAccountViewHolder(val binding: ItemAccountFragmentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bankAccount: BankAccount){
            binding.imgBank.setImageResource(bankAccount.bankImageId?: DEFAULT_BANK_IMAGE)
            binding.textAccountName.text = String.format("%.2f р", bankAccount.balance)
            binding.textExpenseType.text = bankAccount.cardType.toString()
            binding.textExpense.text = bankAccount.cardPan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        val binding = ItemAccountFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankAccountViewHolder(binding)
    }

    override fun getItemCount(): Int =
        bankAccountsList.size


    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        holder.bind(bankAccountsList[position])
    }

    fun setData(accountList: List<BankAccount>){
        this.bankAccountsList =  accountList
        notifyDataSetChanged()
    }
}
