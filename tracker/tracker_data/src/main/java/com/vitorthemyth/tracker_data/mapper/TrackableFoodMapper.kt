package com.vitorthemyth.tracker_data.mapper

import com.example.tracker_domain.model.TrackableFood
import com.vitorthemyth.tracker_data.remote.dto.Product
import kotlin.math.roundToInt


fun Product.toTrackableFood(): TrackableFood? {

    val carbsPer100g = this.nutriments.carbohydrates100g.roundToInt()
    val proteinsPer100g = this.nutriments.proteins100g.roundToInt()
    val fatPer100g = this.nutriments.fat100g.roundToInt()
    val caloriesPer100g = this.nutriments.energyKcal100g.roundToInt()

    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageFrontThumbUrl,
        caloriesPer100g = caloriesPer100g,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinsPer100g,
        fatPer100g = fatPer100g
    )
}

