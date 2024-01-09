package com.example.budget.view.fragments.expenseItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.budget.R


/** An adapter that displays [CarouselItem]s for a Carousel.  */
internal class CarouselAdapter @JvmOverloads constructor(

    private val listener: CarouselItemListener,
    @field:LayoutRes @param:LayoutRes private val itemLayoutRes: Int = R.layout.carousel_expense_item_icons
) : ListAdapter<CarouselItem?, CarouselItemViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): CarouselItemViewHolder {
        return CarouselItemViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(itemLayoutRes, viewGroup, false), listener
        )
    }

    override fun onBindViewHolder(carouselItemViewHolder: CarouselItemViewHolder, pos: Int) {
        carouselItemViewHolder.bind(getItem(pos)!!)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<CarouselItem?> =
            object : DiffUtil.ItemCallback<CarouselItem?>() {
                override fun areItemsTheSame(
                    oldItem: CarouselItem, newItem: CarouselItem
                ): Boolean {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: CarouselItem, newItem: CarouselItem
                ): Boolean {
                    return false
                }
            }
    }
}