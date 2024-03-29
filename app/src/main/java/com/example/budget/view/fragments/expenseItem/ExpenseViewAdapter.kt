package com.example.budget.view.fragments.expenseItem

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.ItemExpenseItemsBinding
import com.example.budget.model.database.entity.SellerEntity
import com.example.budget.model.domain.BudgetGroupWithAmount
import timber.log.Timber

class ExpenseViewAdapter(
    private var budgetGroupWithAmounts: List<BudgetGroupWithAmount>,
    private var sellers: List<SellerEntity>,
    private val onItemClickListener: (budgetGroup: BudgetGroupWithAmount, sellers: List<SellerEntity>) -> Unit
) : RecyclerView.Adapter<ExpenseViewAdapter.ExpenseViewHolder>() {
    var totalOperationAmount = 0L

    class ExpenseViewHolder(val binding: ItemExpenseItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(budgetGroupWithAmount: BudgetGroupWithAmount, totalOperationAmount: Long) {

            with(binding) {
                textExpense.text = budgetGroupWithAmount.name
                checkImage(budgetGroupWithAmount.iconResId)?.let {
                    budgetGroupImage.setImageResource(it)
                }
                totalAmount.text = formatAmount(budgetGroupWithAmount.totalAmount)
                budgetGroupWithAmount.totalAmount?.let {
                    assert(totalOperationAmount.toInt() != 0)
                    expenseLinearIndicator.progress = (it / totalOperationAmount * 100).toInt()

                }
            }
        }

        private fun formatAmount(number: Number?): String? {
            return if (number == null) {
                "0 р."
            } else {
                DecimalFormat("#,###,###").format(number)
                    .replace(",", " ") + " р."
            }
        }

        private fun checkImage(image: Int?): Int? {
            return if (image == 0) R.drawable.ic_empty_expense_24
            else image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        totalOperationAmount =
            budgetGroupWithAmounts.map { it.totalAmount?.toLong() }.mapNotNull { it }.sum()
        val binding =
            ItemExpenseItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int =
        budgetGroupWithAmounts.size


    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = budgetGroupWithAmounts.get(position)
        holder.bind(current, totalOperationAmount)
        holder.itemView.setOnClickListener {
            onItemClickListener(current, sellers)
        }
    }

    fun setData(
        newBudgetGroupWithAmount: List<BudgetGroupWithAmount>,
        newSellers: List<SellerEntity>
    ) {
        this.budgetGroupWithAmounts = newBudgetGroupWithAmount
        this.sellers = newSellers
        notifyDataSetChanged()
    }
}