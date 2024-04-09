package com.example.tracker_domain.model

sealed class MealType(val name:String) {
    object Breakfast : MealType("breakfast")
    object Lunch : MealType("lunch")
    object Dinner : MealType("dinner")
    object Snack : MealType("snack")

    companion object{
        fun fromString(name: String) : MealType{
            return when(name){
                Breakfast.name-> Breakfast
                Lunch.name-> Lunch
                Dinner.name-> Dinner
                Snack.name-> Snack
                else-> Breakfast
            }
        }
    }
}