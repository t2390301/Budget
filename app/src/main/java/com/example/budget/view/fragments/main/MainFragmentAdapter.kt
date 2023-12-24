package com.example.budget.view.fragments.main

import android.icu.text.DateFormat
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.ItemMainFragmentBinding
import com.example.budget.model.domain.BudgetEntryTable
import com.example.budget.model.domain.OperationType
import timber.log.Timber
import java.util.Locale

class MainFragmentAdapter(
    private var budgetEntitiesTableList: List<BudgetEntryTable>?
) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var _binding: ItemMainFragmentBinding? = null
    private val binding get() = _binding!!
    private var decimalFormat = DecimalFormat("#,###,###")

    class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val textAccount: TextView =
            itemView.findViewById(R.id.text_account)          // Название счета
        val textDateAndTime: TextView = itemView.findViewById(R.id.text_date_and_time)   // Дата
        val textExpense: TextView = itemView.findViewById(R.id.text_expense)  // Статья расходов
        val textAmount: TextView = itemView.findViewById(R.id.text_amount)   // Сумма р.
        val imgBank: ImageView = itemView.findViewById(R.id.img_bank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MainViewHolder {

        val binding = ItemMainFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MainViewHolder(binding.root)
    }

    fun setList(list: List<BudgetEntryTable>) {
        this.budgetEntitiesTableList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = budgetEntitiesTableList?.size?: 0

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = budgetEntitiesTableList?.get(position)
        if (item != null) {
            holder.textAccount.text = item.cardPan
            holder.textExpense.text = item.budgetGroupName
            holder.textDateAndTime.text = formatToRusShortDate.format(item.date)
            holder.textAmount.text = item.operationAmount.formatToText(item.operationType)
            holder.imgBank.setBackgroundResource(item.bankImageId)
//            holder.imgBank.setImageResource(item.bankImageId)
            holder.imgBank.setBackgroundColor(com.google.android.material.R.color.m3_ref_palette_white)
        }
   }

    private var formatToRusShortDate: DateFormat =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("ru"))


    private fun Number.formatToText(operationType: OperationType) =
        if (operationType == OperationType.EXPENSE) {
            "- " + decimalFormat.format(this).replace(",", " ") + " р."
        } else {
            "+ " + decimalFormat.format(this).replace(",", " ") + " р."
        }

}

