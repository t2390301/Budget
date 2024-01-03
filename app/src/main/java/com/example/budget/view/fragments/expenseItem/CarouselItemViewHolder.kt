package com.example.budget.view.fragments.expenseItem

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.budget.R

/** An [RecyclerView.ViewHolder] that displays an item inside a Carousel.  */
internal class CarouselItemViewHolder(itemView: View, listener: CarouselItemListener) :
    RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView
    private val listener: CarouselItemListener

    init {
        imageView = itemView.findViewById<ImageView>(R.id.carousel_image_view)
        this.listener = listener
    }

    fun bind(item: CarouselItem) {
        Glide.with(imageView.context).load(item.drawableRes).centerCrop().into(imageView)
        imageView.contentDescription = imageView.resources.getString(item.contentDescRes)


        itemView.setOnClickListener(View.OnClickListener { v: View? ->
            listener.onItemClicked(
                item,
                getBindingAdapterPosition()
            )
        })
    }
}