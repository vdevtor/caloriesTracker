package com.example.onboarding_presentation.nutrients_goal

sealed class NutrientGoalEvent {
    data class onCarbRatioEnter(val ratio : String) : NutrientGoalEvent()
    data class onProteinRatioEnter(val ratio : String) : NutrientGoalEvent()
    data class onFatRatioEnter(val ratio : String) : NutrientGoalEvent()

    object onNextClick : NutrientGoalEvent()
}