package com.example.budget.view.fragments.main

import android.icu.text.DateFormat
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemMainFragmentBinding
import com.example.budget.model.database.converters.Converters
import com.example.budget.model.database.converters.OperationTypeConverter
import com.example.budget.model.database.entity.BudgetEntryEntity
import com.example.budget.model.domain.OperationType

import java.util.Locale

class MainFragmentAdapter(
    private val values: List<BudgetEntryEntity>
) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var decimalFormat = DecimalFormat("#,###,###")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMainFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = values[position]
        holder.textAccount.text = item.bankAccountId.toString()
        holder.textExpense.text = item.sellerId.toString()
        holder.textDateAndTime.text = formatToRusShortDate.format(item.date)
        holder.textAmount.text = item.operationAmount.formatToText(item.operationType)


    }

    private var formatToRusShortDate: DateFormat =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("ru"))

    private fun Number.formatToText(operationType: OperationType) =
        if (operationType == OperationType.EXPENSE) {
            "- " + decimalFormat.format(this).replace(",", " ") + " р."
        } else {
            "+ " + decimalFormat.format(this).replace(",", " ") + " р."
        }


    inner class MainViewHolder(binding: ItemMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textAccount: TextView = binding.textAccount          // Название счета
        val textDateAndTime: TextView = binding.textDateAndTime  // Дата
        val textExpense: TextView = binding.textExpense // Статья расходов
        val textAmount: TextView = binding.textAmount   // Сумма р.
        val imgBank: ImageView = binding.imgBank
    }

}

