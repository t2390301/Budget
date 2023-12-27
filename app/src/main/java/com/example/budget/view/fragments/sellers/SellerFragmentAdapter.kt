package com.example.budget.view.fragments.sellers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.databinding.ItemSellersFragmentBinding
import com.example.budget.model.domain.Seller

class SellerFragmentAdapter(private var sellersList: MutableList<Seller>,
                           private val onItemClicked:(position: Int) -> Unit):
RecyclerView.Adapter<SellerFragmentAdapter.SellerViewHolder>(){
    class SellerViewHolder(val binding: ItemSellersFragmentBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(seller: Seller){
                binding.sellerName.text = seller.name
                binding.budgetGroup.text = seller.budgetGroupName.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {
        val binding = ItemSellersFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SellerViewHolder(binding)
    }

    override fun getItemCount(): Int =
        sellersList.size


    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {
        val curent = sellersList.get(position)
        holder.itemView.setOnClickListener{
            onItemClicked(position)
        }
        holder.bind(curent)
    }

    fun setData(newSellersList: MutableList<Seller>){
        this.sellersList = newSellersList
        notifyDataSetChanged()
    }

}
