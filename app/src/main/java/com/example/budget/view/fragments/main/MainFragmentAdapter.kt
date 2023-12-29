package com.example.budget.view.fragments.main

import android.icu.text.DateFormat
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemMainFragmentBinding
import com.example.budget.model.constants.DEFAULT_BANK_IMAGE
import com.example.budget.model.domain.BudgetEntryTable
import com.example.budget.model.domain.OperationType
import java.util.Locale

class MainFragmentAdapter(
    private var budgetEntitiesTableList: List<BudgetEntryTable>?
) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var decimalFormat = DecimalFormat("#,###,###")

    class MainViewHolder(val binding: ItemMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(budgetEntryTable: BudgetEntryTable) {
            binding.textAccount.text = budgetEntryTable.cardPan
            binding.textExpense.text = budgetEntryTable.budgetGroupName
//            binding.textDateAndTime.text=
            binding.textAmount.text = budgetEntryTable.operationAmount.toString()
            binding.accountImgBank.setImageResource(budgetEntryTable.bankImageId ?: DEFAULT_BANK_IMAGE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MainViewHolder{
        val binding =
            ItemMainFragmentBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = budgetEntitiesTableList?.get(position)
        if (item != null) {
            holder.bind(budgetEntitiesTableList?.get(position)!!)
        }
    }

    /*
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val item = budgetEntitiesTableList?.get(position)
            if (item != null) {
                holder.textAccount.text = item.cardPan
                holder.textExpense.text = item.budgetGroupName
                holder.textDateAndTime.text = formatToRusShortDate.format(item.date)
                holder.textAmount.text = item.operationAmount.formatToText(item.operationType)
                holder.imgBank.setImageResource(item.bankImageId)
            }
        }
    */
    /*    class MainViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            val textAccount: TextView =
                itemView.findViewById(R.id.text_account)          // Название счета
            val textDateAndTime: TextView = itemView.findViewById(R.id.text_date_and_time)   // Дата
            val textExpense: TextView = itemView.findViewById(R.id.text_expense)  // Статья расходов
            val textAmount: TextView = itemView.findViewById(R.id.text_amount)   // Сумма р.
            val imgBank: ImageView = itemView.findViewById(R.id.account_img_bank)
        }*/

    override fun getItemCount(): Int = budgetEntitiesTableList?.size ?: 0

    fun setList(list: List<BudgetEntryTable>) {
        this.budgetEntitiesTableList = list
        notifyDataSetChanged()
    }

    var formatToRusShortDate: DateFormat =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("ru"))

    private fun Number.formatToText(operationType: OperationType) =
        if (operationType == OperationType.EXPENSE) {
            "- " + decimalFormat.format(this).replace(",", " ") + " р."
        } else {
            "+ " + decimalFormat.format(this).replace(",", " ") + " р."
        }

}

