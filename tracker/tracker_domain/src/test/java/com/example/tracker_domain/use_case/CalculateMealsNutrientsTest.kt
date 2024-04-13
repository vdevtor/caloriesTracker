package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import com.vitorthemyth.core.domain.Preferences
import com.vitorthemyth.core.model.ActivityLevel
import com.vitorthemyth.core.model.Gender
import com.vitorthemyth.core.model.GoalType
import com.vitorthemyth.core.model.UserInfo
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealsNutrientsTest {


   private lateinit var calculateMealsNutrients: CalculateMealsNutrients


   @Before
   fun setUp() {
      val preferences = mockk<Preferences>(relaxed = true)
      every { preferences.loadUserInfo() } returns UserInfo(
         gender = Gender.Male,
         age = 27,
         height = 180,
         weight = 77f,
         activityLevel = ActivityLevel.Medium,
         goalType = GoalType.KeepWeight,
         carbRatio = 0.4f,
         proteinRatio = 0.3f,
         fatRatio = 0.3f

      )
      calculateMealsNutrients = CalculateMealsNutrients(preferences)
   }

   @Test
   fun `Calories for breakfast properly calculated`() {
      val trackedFoods = (1..30).map {
         TrackedFood(
            name = "name",
            imageUrl = null,
            carbs = Random.nextInt(100),
            protein = Random.nextInt(100),
            fat = Random.nextInt(100),
            mealType = com.example.tracker_domain.model.MealType.fromString(
               listOf("breakfast","lunch","dinner","snacks").random()
            ),
            amount = 100,
            date = LocalDate.now(),
            calories = Random.nextInt(2000)
         )
      }
      val result = calculateMealsNutrients(trackedFoods)

      val breakFastCalories = result.mealNutrients
         .values.filter {
            it.mealType == MealType.Breakfast
         }
         .sumOf { it.caloriesAmount }


      val expectedCalories = trackedFoods.filter {
            it.mealType == MealType.Breakfast
         }.sumOf { it.calories }

      assertThat(breakFastCalories).isEqualTo(expectedCalories)

   }

   @Test
   fun `Carbs for dinner properly calculated`() {
      val trackedFoods = (1..30).map {
         TrackedFood(
            name = "name",
            imageUrl = null,
            carbs = Random.nextInt(100),
            protein = Random.nextInt(100),
            fat = Random.nextInt(100),
            mealType = com.example.tracker_domain.model.MealType.fromString(
               listOf("breakfast","lunch","dinner","snacks").random()
            ),
            amount = 100,
            date = LocalDate.now(),
            calories = Random.nextInt(2000)
         )
      }
      val result = calculateMealsNutrients(trackedFoods)

      val dinnerFastCarbs = result.mealNutrients
         .values.filter {
            it.mealType == MealType.Dinner
         }
         .sumOf { it.carbsAmount }


      val expectedCarbs = trackedFoods.filter {
         it.mealType == MealType.Dinner
      }.sumOf { it.carbs }

      assertThat(dinnerFastCarbs).isEqualTo(expectedCarbs)

   }
}