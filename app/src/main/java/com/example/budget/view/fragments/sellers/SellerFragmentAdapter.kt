package com.example.budget.view.fragments.sellers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemSellersFragmentBinding
import com.example.budget.model.database.entity.BudgetGroupEntity
import com.example.budget.model.domain.Seller

class SellerFragmentAdapter(
    private var sellersList: MutableList<Seller>,
    private var budgetGroup: List<BudgetGroupEntity>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<SellerFragmentAdapter.SellerViewHolder>() {

    companion object{
        const val TAG = "SellerFragmentAdapter"
    }

    private fun initSpinnerArray():MutableList<String> {
        var list: MutableList<String> = mutableListOf()
        for (bg in budgetGroup) {
            list.add(bg.name)
        }
        return list
    }


    class SellerViewHolder(val binding: ItemSellersFragmentBinding, val spinnerArray: List<String>) :
        RecyclerView.ViewHolder(binding.root) {
        

        val spinnerAdapter = ArrayAdapter(
            itemView.context,
            android.R.layout.simple_spinner_item,
            spinnerArray as ArrayList<String>
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        fun bind(seller: Seller) {
            binding.sellerName.text = seller.name
            binding.budgetGroup.adapter = spinnerAdapter
            binding.budgetGroup.setSelection(spinnerArray.indexOf(seller.budgetGroupName))
            binding.budgetGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    seller.budgetGroupName = spinnerArray.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {
        val spinnerArray = initSpinnerArray()
        val binding =
            ItemSellersFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SellerViewHolder(binding, spinnerArray)
    }

    override fun getItemCount(): Int =
        sellersList.size


    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {
        val curent = sellersList.get(position)
        /*holder.itemView.setOnClickListener {
            onItemClicked(position)
        }*/
        holder.bind(curent)
    }



    fun setData(newSellersList: MutableList<Seller>) {
        this.sellersList = newSellersList
        notifyDataSetChanged()
    }

    fun setGroups(budgetGroups: List<BudgetGroupEntity>) {
        this.budgetGroup = budgetGroups
    }

}
