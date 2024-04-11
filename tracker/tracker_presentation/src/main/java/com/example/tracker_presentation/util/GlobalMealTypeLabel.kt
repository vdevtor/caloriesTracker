package com.example.tracker_presentation.util

import android.content.Context
import com.example.tracker_domain.model.MealType


fun String.toMealTypeTranslated(context: Context) : String{
    return when(this){

        MealType.Breakfast.name->{
            context.getString(com.vitorthemyth.core.R.string.breakfast)
        }
        MealType.Lunch.name->{
            context.getString(com.vitorthemyth.core.R.string.lunch)
        }
        MealType.Dinner.name->{
            context.getString(com.vitorthemyth.core.R.string.dinner)
        }
        MealType.Snack.name->{
            context.getString(com.vitorthemyth.core.R.string.snacks)
        }

        else -> context.getString(com.vitorthemyth.core.R.string.snacks)
    }
}