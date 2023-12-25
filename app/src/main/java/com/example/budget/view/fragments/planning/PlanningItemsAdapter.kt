package com.example.budget.view.fragments.planning

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.ItemPlanningNoteBinding
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.domain.PlanningNote

class PlanningItemsAdapter(
    private val values: List<PlanningNote>
) : RecyclerView.Adapter<PlanningItemsAdapter.ViewHolder>() {

    var decimalFormat = DecimalFormat("#,###,###")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPlanningNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.budgetGroup.name
            .replace("_", " ")
            .capitalizeFirstCharacter()
        holder.value.text = item.value.formatToText()

        val iconId = when (item.budgetGroup) {
            BudgetGroupEnum.ПРОДУКТЫ -> R.drawable.ic_fastfood
            else -> R.drawable.ic_bills_list
            /*
            BudgetGroupEnum.ТРАНСПОРТ -> TODO()
            BudgetGroupEnum.РАЗВЛЕЧЕНИЯ -> TODO()
            BudgetGroupEnum.УСЛУГИ -> TODO()
            BudgetGroupEnum.ДОМАШНЕЕ_ХОЗЯЙСТВО -> TODO()
            BudgetGroupEnum.ЕДА_ВНЕ_ДОМА -> TODO()*/
        }

        holder.icon.setImageDrawable(
            holder.icon.resources.getDrawable(iconId)
        )
    }

    private fun Number.formatToText() =
        decimalFormat.format(this).replace(",", " ") + " р."

    private fun String.capitalizeFirstCharacter() =
        substring(0, 1).uppercase() + substring(1).lowercase()

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemPlanningNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val value: TextView = binding.value
        val icon: ImageView = binding.icon
    }

}