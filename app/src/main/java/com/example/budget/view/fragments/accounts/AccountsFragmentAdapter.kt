package com.example.budget.view.fragments.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemAccountFragmentBinding
import com.example.budget.model.domain.BankAccount

class AccountsFragmentAdapter(
    private var bankAccountsList: List<BankAccount>,
    private val onItemClicked: (BankAccount) -> Unit
) :
    RecyclerView.Adapter< AccountsFragmentAdapter.BankAccountViewHolder>() {


    class BankAccountViewHolder(val binding: ItemAccountFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bankAccount: BankAccount) {
            binding.accountImgBank.         //setImageBitmap(BitmapFactory.decodeResource(itemView.resources, bankAccount.bankImageId?: DEFAULT_BANK_IMAGE, null))
            setImageDrawable(
                bankAccount.bankImageId?.let {
                    ResourcesCompat.getDrawable(itemView.resources,
                         it, null)
                })
            //binding.accountImgBank.setImageResource(bankAccount.bankImageId ?: DEFAULT_BANK_IMAGE)
            binding.textAccountName.text = String.format("%.2f р", bankAccount.balance)
            binding.textExpenseType.text = bankAccount.cardType.toString()
            binding.textExpense.text = bankAccount.cardPan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        val binding =
            ItemAccountFragmentBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return BankAccountViewHolder(binding)
    }

    override fun getItemCount(): Int =
        bankAccountsList.size


    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        val current = bankAccountsList.get(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(bankAccountsList[position])
    }


    fun setData(accountList: List<BankAccount>) {
        this.bankAccountsList = accountList
        notifyDataSetChanged()
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BankAccount>() {
            override fun areItemsTheSame(oldItem: BankAccount, newItem: BankAccount): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: BankAccount, newItem: BankAccount): Boolean {
                return oldItem.cardPan == newItem.cardPan
            }

        }
    }

}

