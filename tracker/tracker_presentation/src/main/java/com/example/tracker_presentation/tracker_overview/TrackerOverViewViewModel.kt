package com.example.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker_domain.use_case.TrackerUseCases
import com.vitorthemyth.core.domain.Preferences
import com.vitorthemyth.core.navigation.Route
import com.vitorthemyth.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverViewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {
    var state by mutableStateOf(TrackerOverViewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getsFoodForDateJob: Job? = null

    init {
        preferences.saveShouldShowOnBoarding(shouldShow = false)
    }

    fun onEvent(event: TrackerOverViewEvent) {
        when (event) {
            is TrackerOverViewEvent.OnAddFoodFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.SEARCH
                                    + "/${event.meal.name}"
                                    + "/${state.date.dayOfMonth}"
                                    + "/${state.date.monthValue}"
                                    + "/${state.date.year}"
                        )
                    )
                }
            }

            is TrackerOverViewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    refreshFoods()
                }
            }

            is TrackerOverViewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }

            is TrackerOverViewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }

            is TrackerOverViewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name){
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
        }
    }

    private fun refreshFoods() {
        getsFoodForDateJob?.cancel()
        getsFoodForDateJob = trackerUseCases
            .getFoodsForDate(state.date)
            .onEach { foods ->
                val nutrientsResult = trackerUseCases.calculateMealsNutrients(foods)
                state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinsGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFood = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrientsResult.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0
                                )

                        it.copy(
                            carbs = nutrientsForMeal.carbsAmount,
                            protein = nutrientsForMeal.proteinAmount,
                            fat = nutrientsForMeal.fatAmount,
                            calories = nutrientsForMeal.caloriesAmount
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }
}