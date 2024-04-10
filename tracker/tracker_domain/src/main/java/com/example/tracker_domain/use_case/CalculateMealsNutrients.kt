package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import com.vitorthemyth.core.domain.Preferences
import com.vitorthemyth.core.model.ActivityLevel
import com.vitorthemyth.core.model.Gender
import com.vitorthemyth.core.model.GoalType
import com.vitorthemyth.core.model.UserInfo
import kotlin.math.roundToInt

class CalculateMealsNutrients(
    private val preferences: Preferences
) {


    operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entries ->
                val type = entries.key
                val foods = entries.value

                MealNutrients(
                    carbsAmount = foods.sumOf { it.carbs },
                    proteinAmount = foods.sumOf { it.protein },
                    fatAmount = foods.sumOf { it.fat },
                    caloriesAmount = foods.sumOf { it.calories },
                    mealType = type
                )
            }
        val totalCarbs = allNutrients.values.sumOf { it.carbsAmount }
        val totalProtein = allNutrients.values.sumOf { it.proteinAmount }
        val totalFat = allNutrients.values.sumOf { it.fatAmount }
        val totalCalories = allNutrients.values.sumOf { it.caloriesAmount }

        val userInfo = preferences.loadUserInfo()
        val caloryGoal = dailyCaloryRequirement(userInfo)
        val carbsGoal = (caloryGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinsGoal = (caloryGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinsGoal = proteinsGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }
    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }

    data class MealNutrients(
        val carbsAmount: Int,
        val proteinAmount: Int,
        val fatAmount: Int,
        val caloriesAmount: Int,
        val mealType: MealType,
    )

    data class Result(
        val carbsGoal: Int,
        val proteinsGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}