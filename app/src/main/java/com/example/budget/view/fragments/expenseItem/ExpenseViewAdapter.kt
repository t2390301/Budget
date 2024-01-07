package com.example.budget.view.fragments.expenseItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.ItemExpenseItemsBinding
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.domain.BudgetGroupWithAmount

class ExpenseViewAdapter(private var budgetGroupWithAmounts: List<BudgetGroupWithAmount>,
                         private var sellers: List<SellerEntity>,
                         private val onItemClickListener: (budgetGroup: BudgetGroupWithAmount, sellers: List<SellerEntity>) -> Unit
)
    : RecyclerView.Adapter<ExpenseViewAdapter.ExpenseViewHolder>() {
    var totalOperationAmount = 0L

    class ExpenseViewHolder(val binding:ItemExpenseItemsBinding):RecyclerView.ViewHolder(binding.root) {


        fun bind(budgetGroupWithAmount: BudgetGroupWithAmount){

            with(binding){
                textExpense.text = budgetGroupWithAmount.name
                budgetGroupImage.setImageResource(budgetGroupWithAmount.iconResId?: R.drawable.ic_yandexdisk_64)

                totalAmount.text = String.format("%.2f р", budgetGroupWithAmount.totalAmount)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        totalOperationAmount = budgetGroupWithAmounts.map{it.totalAmount?.toLong()}.mapNotNull { it }. sum()
        val binding = ItemExpenseItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int =
        budgetGroupWithAmounts.size


    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = budgetGroupWithAmounts.get(position)
        holder.bind(current)
        holder.itemView.setOnClickListener{
            onItemClickListener(current, sellers)
        }
    }

    fun setData(newBudgetGroupWithAmount: List<BudgetGroupWithAmount>, newSellers: List<SellerEntity> ){
        this.budgetGroupWithAmounts = newBudgetGroupWithAmount
        this.sellers = newSellers
        notifyDataSetChanged()
    }
}