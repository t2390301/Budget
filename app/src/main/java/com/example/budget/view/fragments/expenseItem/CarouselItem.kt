package com.example.budget.view.fragments.expenseItem

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * A data class that holds all information related to an item inside a Carousel.
 */
internal class CarouselItem(
    @field:DrawableRes @get:DrawableRes
    @param:DrawableRes val drawableRes: Int, @field:StringRes @get:StringRes
    @param:StringRes val contentDescRes: Int
)