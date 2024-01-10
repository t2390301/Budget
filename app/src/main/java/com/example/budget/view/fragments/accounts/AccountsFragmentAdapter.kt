package com.example.budget.view.fragments.accounts

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.ItemAccountFragmentBinding
import com.example.budget.model.domain.BankAccount
import timber.log.Timber

class AccountsFragmentAdapter(
    private var bankAccountsList: List<BankAccount>,
    private val onItemClicked: (BankAccount) -> Unit
) :
    RecyclerView.Adapter<AccountsFragmentAdapter.BankAccountViewHolder>() {


    class BankAccountViewHolder(val binding: ItemAccountFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bankAccount: BankAccount) {
            /*            binding.accountImgBank.
                        setImageDrawable(
                            bankAccount.bankImageId?.let {
                                ResourcesCompat.getDrawable(
                                    itemView.resources,
                                    it, null
                                )
                            })
                        bankAccount.bankImageId?.let { binding.accountImgBank.setImageResource(it) }
                        setImageBitmap(BitmapFactory.decodeResource(itemView.resources, bankAccount.bankImageId?: DEFAULT_BANK_IMAGE, null))*/
            binding.accountImgBank.setImageResource(checkImage(bankAccount.bankImageId))
            binding.textAccountName.text = bankAccount.balance.formatToText()
            binding.textExpenseType.text = bankAccount.cardType.toString()
            binding.textExpense.text = bankAccount.cardPan
        }

        private fun Number.formatToText() =
            DecimalFormat("#,###,###").format(this).replace(",", " ") + " р."

        private fun checkImage(image: Int?): Int {
            return image ?: R.drawable.ic_empty_expense_24
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        val binding =
            ItemAccountFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return BankAccountViewHolder(binding)
    }

    override fun getItemCount(): Int =
        bankAccountsList.size


    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        val current = bankAccountsList[position]
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

