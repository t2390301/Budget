package com.example.budget.view.fragments.main

import android.icu.text.DateFormat
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemMainFragmentBinding
import com.example.budget.model.domain.BudgetEntryTable
import com.example.budget.model.domain.OperationType
import java.util.Locale

class MainFragmentAdapter(
    private var budgetEntitiesTableList: List<BudgetEntryTable>?
) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    class MainViewHolder(val binding: ItemMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(budgetEntryTable: BudgetEntryTable) {
            binding.textAccount.text = budgetEntryTable.cardPan
            binding.textExpense.text = budgetEntryTable.budgetGroupName
            binding.textDateAndTime.text = formatToRusShortDate.format(budgetEntryTable.date)
            binding.textAmount.text = budgetEntryTable.operationAmount.formatToText(budgetEntryTable.operationType)
            binding.accountImgBank.setImageResource(budgetEntryTable.bankImageId)
        }

        private var formatToRusShortDate: DateFormat =
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("ru"))

        private fun Number.formatToText(operationType: OperationType) =
            if (operationType == OperationType.EXPENSE) {
                "- "
            } else {
                "+ "
            } + DecimalFormat("#,###,###").format(this).replace(",", " ") + " р."
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemMainFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = budgetEntitiesTableList?.get(position)
        if (item != null) {
            holder.bind(budgetEntitiesTableList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int = budgetEntitiesTableList?.size ?: 0

    fun setList(list: List<BudgetEntryTable>) {
        this.budgetEntitiesTableList = list
        notifyDataSetChanged()
    }


}

