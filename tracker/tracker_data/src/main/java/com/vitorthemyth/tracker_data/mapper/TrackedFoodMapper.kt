package com.vitorthemyth.tracker_data.mapper

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import com.vitorthemyth.tracker_data.local.entity.TrackedFoodEntity
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {

    return TrackedFood(
        name = name,
        imageUrl = imageUrl,
        carbs = carbs,
        protein = protein,
        fat = fat,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {

    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        year = date.year,
        month = date.monthValue,
        calories = calories,
        id = id
    )
}