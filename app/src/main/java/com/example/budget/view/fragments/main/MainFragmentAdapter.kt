package com.example.budget.view.fragments.main

import android.icu.text.DateFormat
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemMainFragmentBinding
import com.example.budget.model.database.entity.BudgetEntryEntity

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
        holder.textDateAndTime.text = formatToRusShortDate.format(item.date)
        holder.textAmount.text = item.operationAmount.formatToText()

    }

    private var formatToRusShortDate: DateFormat =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale("ru"))

    private fun Number.formatToText() =
        decimalFormat.format(this).replace(",", " ") + " р."

    inner class MainViewHolder(binding: ItemMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textAccount: TextView = binding.textAccount
        val textDateAndTime: TextView = binding.textDateAndTime
        val textExpense: TextView = binding.textExpense
        val textAmount: TextView = binding.textAmount
        val imgBank: ImageView = binding.imgBank
    }

}

