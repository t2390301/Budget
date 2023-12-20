package com.example.budget.view.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemMainFragmentBinding
import com.example.budget.model.database.entity.BudgetEntryEntity

class MainFragmentAdapter(
    private val values: List<BudgetEntryEntity>
) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

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
        holder.textDateAndTime.text = item.date.toString()
        holder.textAmount.text = item.operationAmount.toString()

    }

    inner class MainViewHolder(binding: ItemMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textAccount: TextView = binding.textAccount
        val textDateAndTime: TextView = binding.textDateAndTime
        val textExpense: TextView = binding.textExpense
        val textAmount: TextView = binding.textAmount
        val imgBank: ImageView = binding.imgBank
    }

}